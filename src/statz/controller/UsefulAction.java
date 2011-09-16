package statz.controller;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;

@UrlBinding("/action/Useful")
public class UsefulAction extends BaseAction {
	@DefaultHandler
    public Resolution authenticate() {
		if(login()) {
	    	return new ForwardResolution("/WEB-INF/jsp/useful.jsp");
		} else {
			return new ForwardResolution("/action/Index");
		}
	}  
}
