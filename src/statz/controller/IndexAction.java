package statz.controller;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.validation.SimpleError;

@UrlBinding("/action/Index")
public class IndexAction extends BaseAction {
	
	@DefaultHandler
    public Resolution authenticate() {
		if(login()) {
			return new RedirectResolution("/action/List");
		} else {
			if(getContext().getRequest().getSession().getAttribute("username") != null) {
				getContext().getValidationErrors().addGlobalError(
	                new SimpleError("Wrong user name or password", new Object()));
			}
			return new ForwardResolution("/WEB-INF/jsp/index.jsp");
		}
	}  
}
