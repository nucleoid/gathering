package statz.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletResponse;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.util.Log;

import org.jmesa.core.CoreContext;
import org.jmesa.core.CoreContextFactory;
import org.jmesa.core.CoreContextFactoryImpl;
import org.jmesa.limit.Limit;
import org.jmesa.limit.LimitFactory;
import org.jmesa.limit.LimitFactoryImpl;
import org.jmesa.view.TableFactory;
import org.jmesa.view.View;
import org.jmesa.view.ViewExporter;
import org.jmesa.view.component.Table;
import org.jmesa.view.csv.CsvTableFactory;
import org.jmesa.view.csv.CsvView;
import org.jmesa.view.csv.CsvViewExporter;
import org.jmesa.view.editor.BasicCellEditor;
import org.jmesa.view.editor.CellEditor;
import org.jmesa.view.html.HtmlTableFactory;
import org.jmesa.view.html.HtmlView;
import org.jmesa.view.html.component.HtmlTable;
import org.jmesa.view.html.toolbar.Toolbar;
import org.jmesa.view.html.toolbar.ToolbarFactory;
import org.jmesa.view.html.toolbar.ToolbarFactoryImpl;
import org.jmesa.web.HttpServletRequestWebContext;
import org.jmesa.web.WebContext;

@UrlBinding("/List.action")
public class ListAction extends BaseAction {
	
	private static final Log log = Log.getInstance(ListAction.class);
	
	@DefaultHandler
	@DontValidate
	public Resolution authenticate() {
		if(login()) {
	    	return new ForwardResolution("/WEB-INF/jsp/list.jsp");
		} else {
			return new ForwardResolution("Index.action");
		}
	}
	
	public Object getItemTable() {
		ResourceBundle stripesResources = ResourceBundle.getBundle("StripesResources");
		final Integer maxRows = Integer.parseInt(stripesResources.getString("maxRows"));
		final String tableId = "item";
		WebContext webContext = new HttpServletRequestWebContext(getContext().getRequest());
	    Collection<Object> items = new ArrayList<Object>(getThingList());
	    LimitFactory limitFactory = new LimitFactoryImpl(tableId, webContext);
	    Limit limit = limitFactory.createLimitAndRowSelect(maxRows, items.size());
	    CoreContext coreContext = getCoreContext(items, limit, webContext);
	    
	    if (limit.isExportable()) {
	        String type = limit.getExport().getType();
	        if (type.equals("CSV")) {
	        	itemCsvTable(webContext, coreContext, getContext().getResponse());
	            return null;
	        }
	    }
	    
	    Object things = itemHtmlTable(webContext, coreContext);
	   	return things;
	}
	
	public Object getEventTable() {
		ResourceBundle stripesResources = ResourceBundle.getBundle("StripesResources");
		final Integer maxRows = Integer.parseInt(stripesResources.getString("maxRows"));
		final String tableId = "event";
		WebContext webContext = new HttpServletRequestWebContext(getContext().getRequest());
	    Collection<Object> items = new ArrayList<Object>(getEventList());
	    LimitFactory limitFactory = new LimitFactoryImpl(tableId, webContext);
	    Limit limit = limitFactory.createLimitAndRowSelect(maxRows, items.size());
	    CoreContext coreContext = getCoreContext(items, limit, webContext);
	    
	    if (limit.isExportable()) {
	        String type = limit.getExport().getType();
	        if (type.equals("CSV")) {
	        	eventCsvTable(webContext, coreContext, getContext().getResponse());
	            return null;
	        }
	    }
	    
	    Object events = eventHtmlTable(webContext, coreContext);
	   	return events;
	}
	
	public CoreContext getCoreContext(Collection<Object> items, Limit limit, WebContext webContext) {
	    CoreContextFactory factory = new CoreContextFactoryImpl(webContext);
	    CoreContext coreContext = factory.createCoreContext(items, limit);
	    return coreContext;
	}
	
	public Object itemHtmlTable(WebContext webContext, CoreContext coreContext) {
	    HtmlTableFactory tableFactory = new HtmlTableFactory(webContext, coreContext);
	    HtmlTable table = tableFactory.createTable("name", "owner", "quantity", "type.name");
	    table.setCaption("List of Items");
	    ResourceBundle stripesResources = ResourceBundle.getBundle("StripesResources");
	    table.getTableRenderer().setWidth(stripesResources.getString("tableWidth"));	
	    table.getRow().getColumn("type.name").setTitle("Type");
	    ToolbarFactory toolbarFactory = new ToolbarFactoryImpl(table, webContext, coreContext, "csv");
	    Toolbar toolbar = toolbarFactory.createToolbar();
	    View view = new HtmlView(table, toolbar, coreContext);
	    return view.render();
	}
	
	public Object eventHtmlTable(WebContext webContext, CoreContext coreContext) {
	    HtmlTableFactory tableFactory = new HtmlTableFactory(webContext, coreContext);
	    HtmlTable table = tableFactory.createTable("whentime", "name", "location", "duration", "facilitator");
	    table.setCaption("List of Events");
	    ResourceBundle stripesResources = ResourceBundle.getBundle("StripesResources");
	    table.getTableRenderer().setWidth(stripesResources.getString("tableWidth"));	    
	    CellEditor cellEditor = new EventDateEditor(new BasicCellEditor());
	    CellEditor durationCellEditor = new EventDurationEditor(new BasicCellEditor());
	    table.getRow().getColumn("whentime").setTitle("When");
	    table.getRow().getColumn("whentime").getCellRenderer().setCellEditor(cellEditor);
	    table.getRow().getColumn("duration").getCellRenderer().setCellEditor(durationCellEditor);
	    ToolbarFactory toolbarFactory = new ToolbarFactoryImpl(table, webContext, coreContext, "csv");
	    Toolbar toolbar = toolbarFactory.createToolbar();
	    View view = new HtmlView(table, toolbar, coreContext);
	    return view.render();
	}
	
	private static class EventDateEditor implements CellEditor {
	    CellEditor cellEditor;
	    private static String NODATE = "-";
	    public EventDateEditor(CellEditor cellEditor) {
	        this.cellEditor = cellEditor;
	    }

	    public Object getValue(Object item, String property, int rowcount) {
	        Object value = cellEditor.getValue(item, property, rowcount);
	        String formattedDate = "";
	        if(value == null) {
	        	formattedDate = NODATE;
	        } else {
	        	String days[] = { "Unknown", "Sun", "Mon", "Tues", "Wed",
	        			"Thurs", "Fri", "Sat" };
	        	Calendar cal = Calendar.getInstance();
	        	cal.setTime((Date)value);
	        	DateFormat timeFormat = DateFormat.getTimeInstance(DateFormat.SHORT);
	        	formattedDate = days[cal.get(Calendar.DAY_OF_WEEK)] +" " +timeFormat.format(value);        	
	        }
	        return formattedDate;
	    }
	}
	
	private static class EventDurationEditor implements CellEditor {
	    CellEditor cellEditor;
	    private static String NOTIME = "-";
	    public EventDurationEditor(CellEditor cellEditor) {
	        this.cellEditor = cellEditor;
	    }

	    public Object getValue(Object item, String property, int rowcount) {
	        Object value = cellEditor.getValue(item, property, rowcount);
	        String formattedDate = "";
	        if(value == null) {
	        	formattedDate = NOTIME;
	        } else {
	        	Integer intVal = (Integer)value;
	        	BigDecimal hourRound = new BigDecimal(intVal/60, new MathContext(0, RoundingMode.FLOOR));
	    		String hour = hourRound.intValue() == 1 ? " hour " : " hours ";
	    		String min = intVal%60 > 0 ? intVal%60 +" min" : "";
	        	formattedDate = hourRound.intValue() +hour +min;        	
	        }
	        return formattedDate;
	    }
	}
	
	public void eventCsvTable(WebContext webContext, CoreContext coreContext, HttpServletResponse response) {
		TableFactory tableFactory = new CsvTableFactory(webContext, coreContext);
		Table table = tableFactory.createTable("when", "name", "location", "duration", "facilitator");
		View view = new CsvView(table, coreContext);
		ViewExporter exporter = new CsvViewExporter(view, "events.csv", response);
		try {
			exporter.export();
		} catch (Exception e) { //for some reason they throw Exception in jMesa :(
			log.error("Error in eventCsvTable" +e.toString());
		}
	}
	
	public void itemCsvTable(WebContext webContext, CoreContext coreContext, HttpServletResponse response) {
		TableFactory tableFactory = new CsvTableFactory(webContext, coreContext);
		Table table = tableFactory.createTable("name", "owner", "quantity", "type");
		View view = new CsvView(table, coreContext);
		ViewExporter exporter = new CsvViewExporter(view, "items.csv", response);
		try {
			exporter.export();
		} catch (Exception e) { //for some reason they throw Exception in jMesa :(
			log.error("Error in itemCsvTable" +e.toString());
		}
	}
}
