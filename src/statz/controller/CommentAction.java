package statz.controller;

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
import net.sourceforge.stripes.validation.Validate;
import net.sourceforge.stripes.validation.ValidateNestedProperties;
import statz.model.Comment;

import com.mongus.stripes.HibernateProvider;

@UrlBinding("/action/Comment")
public class CommentAction extends BaseAction {

	private static final Log log = Log.getInstance(CommentAction.class);

	@ValidateNestedProperties( { @Validate(field = "name", required = true),
			@Validate(field = "comment", required = true) })
	private Comment entryComment = new Comment();

	@DefaultHandler
	@DontValidate
	public Resolution authenticate() {
		if(login()) {
			return new ForwardResolution("/WEB-INF/jsp/comments.jsp");
		} else {
			return new ForwardResolution("/action/Index");
		}
	}
	
	@HandlesEvent("saveComment")
	public Resolution saveComment() {
			getEntryComment().setPostDate(new Date());
			getSession().save(entryComment);
		HibernateProvider.getInstance().commit();
		log.info("Entered a comment with contents: " +getEntryComment());
		return new RedirectResolution("/action/Comment");
	}

	public Comment getEntryComment() {
		return entryComment;
	}

	public void setEntryComment(Comment entryComment) {
		this.entryComment = entryComment;
	}

	@SuppressWarnings("unchecked")
	public List<Comment> getCommentList() {
		return getSession().createQuery("from Comment order by postDate desc").list();
	}
}
