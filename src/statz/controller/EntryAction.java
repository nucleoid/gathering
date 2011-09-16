package statz.controller;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.StreamingResolution;
import net.sourceforge.stripes.action.UrlBinding;
import statz.model.Thing;

@UrlBinding("/action/Entry")
public class EntryAction extends BaseAction {
	private Thing entryThing = new Thing();
	@DefaultHandler
	public Resolution authenticated() {
		if(login()) {
			setThings(getThingList());
			setEvents(getEventList());
			return new ForwardResolution("/WEB-INF/jsp/entry.jsp");
		} else {
			return new ForwardResolution("/action/Index");
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
	
	public Thing getEntryThing() {
		return entryThing;
	}

	public void setEntryThing(Thing entryThing) {
		this.entryThing = entryThing;
	}
}
