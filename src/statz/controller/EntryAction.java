package statz.controller;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;
import statz.model.Thing;

@UrlBinding("/Entry.action")
public class EntryAction extends BaseAction {
	
	@DefaultHandler
	public Resolution authenticated() {
		if(login()) {
			setThings(getThingList());
			setEvents(getEventList());
			return new ForwardResolution("/WEB-INF/jsp/entry.jsp");
		} else {
			return new ForwardResolution("Index.action");
		}
	}
	
	public Resolution quantityPopulater() {
		Thing current = (Thing)getSession().get(Thing.class, Integer.parseInt(getValue()));
		if(current != null) {
			return new StreamingResolution("text", current.getQuantity().toString());
		} else {
			return new StreamingResolution("text", "");
		}
	}
	
}
