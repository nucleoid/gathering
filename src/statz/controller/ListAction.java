package statz.controller;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.util.Log;

import org.jmesa.view.editor.CellEditor;

@UrlBinding("/action/List")
public class ListAction extends BaseAction {
	
	private static final Log log = Log.getInstance(ListAction.class);
	
	@DefaultHandler
	@DontValidate
	public Resolution authenticate() {
		if(login()) {
	    	return new ForwardResolution("/WEB-INF/jsp/list.jsp");
		} else {
			return new ForwardResolution("/action/Index");
		}
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
}
