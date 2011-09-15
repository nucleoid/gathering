package statz.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletResponse;

import net.sourceforge.stripes.action.ActionBean;
import net.sourceforge.stripes.action.ActionBeanContext;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;

import org.hibernate.Session;
import org.hibernate.criterion.Expression;
import org.hibernate.criterion.MatchMode;

import statz.model.Event;
import statz.model.Thing;
import statz.model.Type;

import com.mongus.stripes.HibernateProvider;

public abstract class BaseAction implements ActionBean {

	private ActionBeanContext context;
	private String username;
	private String password;	
	private String value;
	private List<Thing> things = new ArrayList<Thing>();
	private List<Event> events = new ArrayList<Event>();
	private List<Type> types = new ArrayList<Type>();

	public ActionBeanContext getContext() {
		return context;
	}

	public void setContext(ActionBeanContext context) {
		this.context = context;
	}
	
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
	
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public List<Thing> getThings() {
		return things;
	}

	public void setThings(List<Thing> things) {
		this.things = things;
	}

	public List<Event> getEvents() {
		return events;
	}

	public void setEvents(List<Event> events) {
		this.events = events;
	}

	public List<Type> getTypes() {
		return types;
	}

	public void setTypes(List<Type> types) {
		this.types = types;
	}

	//TODO Convert this round-about authentication to a filter
    public Boolean login() {
		ResourceBundle indexProps = ResourceBundle.getBundle("index");
		this.setUsername(this.getUsername() != null ? this.getUsername() : (String)getContext().getRequest().getSession().getAttribute("username"));
		this.setPassword(this.getPassword() != null ? this.getPassword() : (String)getContext().getRequest().getSession().getAttribute("password"));		
		if(indexProps.getString("username").equals(this.getUsername()) && 
				indexProps.getString("password").equals(this.getPassword())){
			if(getContext().getRequest().getSession().getAttribute("username") == null)
			{
				getContext().getRequest().getSession().setAttribute("username", this.getUsername());
				getContext().getRequest().getSession().setAttribute("password", this.getPassword());
			}
			return true;
		} else{
			return false;
		}
	}     
    
    @HandlesEvent("nameList")
	public Resolution nameList() {
		final StringBuffer names = new StringBuffer();
		names.append("<ul>");
		for(String name : getNameList(getValue()))
		{
			names.append("<li>"+name+"</li>");
		}
		names.append("</ul>");
		return new StreamingResolution("text/xml") {
		    public void stream(HttpServletResponse response) throws Exception {
		        getContext().getResponse().getWriter().write(names.toString());
		    }
		};
	}
	
    @SuppressWarnings("unchecked")
	public List<Thing> getThingList()
	{
		return getSession().createQuery("from Thing order by name").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Event> getEventList()
	{
		return getSession().createQuery("from Event order by whentime asc").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<Type> getTypeList()
	{
		return getSession().createQuery("from Type order by name").list();
	}
	
	@SuppressWarnings("unchecked")
	public List<String> getNameList(String filter) {
		
		getSession().createCriteria(Event.class).add(Expression.like("facilitator", filter, MatchMode.ANYWHERE));
		
		List<String> names = new ArrayList<String>();	
		List<Event> events = getSession().createCriteria(Event.class).add(Expression.like("facilitator", filter, MatchMode.ANYWHERE)).list();
		List<Thing> things = getSession().createCriteria(Thing.class).add(Expression.like("owner", filter, MatchMode.ANYWHERE)).list();
		for(Event event : events){
			if(!names.contains(event.getFacilitator().trim())) {
				names.add(event.getFacilitator().trim());
			}
		}
		for(Thing thing : things){
			if(!names.contains(thing.getOwner().trim())) {
				names.add(thing.getOwner().trim());
			}
		}
		Collections.sort(names);
		return names;
	}
	
	@SuppressWarnings("unchecked")
	public List<Thing> getOpenItemList()
	{
		return getSession().createQuery("from Thing where owner='open' order by name").list();
	}
	
	public Session getSession()
	{
		return HibernateProvider.getInstance().getSession();
	}
}
