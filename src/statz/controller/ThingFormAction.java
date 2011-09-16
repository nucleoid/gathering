package statz.controller;

import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import statz.model.Thing;

import com.mongus.stripes.HibernateProvider;

@UrlBinding("/action/ThingForm")
public class ThingFormAction extends BaseAction {

	@ValidateNestedProperties({
	    @Validate(field="owner", required=true),
	    @Validate(field="quantity", required=true),
	    @Validate(field="type", required=true)
	})
	private Thing entryThing = new Thing();
	
	private Integer itemDropDown;
	
	@HandlesEvent("saveThing")
	public Resolution saveThing()
	{
		if (getEntryThing().getId() == null) {
			getSession().save(entryThing);
		}
		else {
			//in case they tried naming it something else
			String tempOwner = entryThing.getOwner();
			getSession().refresh(entryThing);
			entryThing.setOwner(tempOwner);
			getSession().update(entryThing);
		}
		HibernateProvider.getInstance().commit();
		return new RedirectResolution("/action/List");
	}
	
	public Thing getEntryThing() {
		return entryThing;
	}

	public void setEntryThing(Thing entryThing) {
		this.entryThing = entryThing;
	}

	public Integer getItemDropDown() {
		return itemDropDown;
	}

	public void setItemDropDown(Integer itemDropDown) {
		this.itemDropDown = itemDropDown;
	}   
}
