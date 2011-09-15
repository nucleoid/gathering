package statz.controller;

import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import statz.model.Event;

import com.mongus.stripes.HibernateProvider;

public class EventFormAction extends BaseAction {

	@ValidateNestedProperties({
	    @Validate(field="whentime", required=true, converter=StatzDateTypeConverter.class),
	    @Validate(field="duration", required=true),
	    @Validate(field="facilitator", required=true),
	    @Validate(field="location", required=true),
	    @Validate(field="name", required=true)
	})
	private Event entryEvent = new Event();
	
	@HandlesEvent("saveEvent")
	public Resolution saveEvent()
	{
		if (getEntryEvent().getId() == null) {
			getSession().save(entryEvent);
		}
		HibernateProvider.getInstance().commit();
		return new RedirectResolution("/List.action");
	}

	public Event getEntryEvent() {
		return entryEvent;
	}

	public void setEntryEvent(Event entryEvent) {
		this.entryEvent = entryEvent;
	}    
}
