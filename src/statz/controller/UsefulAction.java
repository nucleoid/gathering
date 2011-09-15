package statz.controller;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.SimpleError;

@UrlBinding("/Useful.action")
public class UsefulAction extends BaseAction {
	@DefaultHandler
    public Resolution authenticate() {
		if(login()) {
	    	return new ForwardResolution("/WEB-INF/jsp/useful.jsp");
		} else {
			return new ForwardResolution("Index.action");
		}
	}  
}
