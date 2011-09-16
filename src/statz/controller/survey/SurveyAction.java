package statz.controller.survey;

import java.util.Date;
import java.util.List;

import net.sourceforge.stripes.action.DefaultHandler;
import net.sourceforge.stripes.action.DontValidate;
import net.sourceforge.stripes.action.ForwardResolution;
import net.sourceforge.stripes.action.HandlesEvent;
import net.sourceforge.stripes.action.RedirectResolution;
import net.sourceforge.stripes.action.Resolution;
import net.sourceforge.stripes.action.UrlBinding;
import net.sourceforge.stripes.util.Log;
import statz.controller.BaseAction;
import statz.controller.CommentAction;
import statz.model.survey.Answer;
import statz.model.survey.Question;
import statz.model.survey.Response;

import com.mongus.stripes.HibernateProvider;

@UrlBinding("/action/Survey")
public class SurveyAction extends BaseAction {

	private static final Log log = Log.getInstance(CommentAction.class);

	private Response response = new Response();
	
	private boolean success = false;

	@DefaultHandler
	@DontValidate
	public Resolution authenticate() {
		if (login()) {
			return new ForwardResolution("/WEB-INF/jsp/survey.jsp");
		} else {
			return new ForwardResolution("/action/Index");
		}
	}

	@HandlesEvent("saveAnswers")
	public Resolution saveAnswers() {
		if (response.getId() == null) {
			response.setDate(new Date());
			for(Answer ans : response.getAnswers()) {
				if(ans.getId() == null) {
					getSession().save(ans);
				}
			}
			getSession().save(response);
		}
		HibernateProvider.getInstance().commit();
		log.info("Entered a survey answer with contents: " + getResponse().toString());
		setSuccess(true);
		return new RedirectResolution("/action/Survey").flash(this);
	}

	public Response getResponse() {
		return response;
	}

	public void setResponse(Response response) {
		this.response = response;
	}
	
	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	@SuppressWarnings("unchecked")
	public List<Question> getQuestionList()
	{
		return getSession().createQuery("from Question where name ='cragunSurvey2007' order by id asc").list();
	}
}
