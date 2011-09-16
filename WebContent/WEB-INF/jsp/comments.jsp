<%@ include file="/WEB-INF/jsp/layout/taglibs.jsp" %>
<stripes:layout-render name="/WEB-INF/jsp/layout/default.jsp">
<stripes:layout-component name="html-head">
	<script type="text/javascript" src="/gathering/js/prototype.js"></script>
	<script type="text/javascript" src="/gathering/js/scriptaculous.js"></script>
	<script type="text/javascript" src="/gathering/js/controls.js"></script>
</stripes:layout-component>
<stripes:layout-component name="commentPage">active</stripes:layout-component>
<stripes:layout-component name="contents">
*Autocompletes your name if you've previously submitted anything.
<div id="forms">
<stripes:errors globalErrorsOnly="true" />
<h3>Enter a question, answer, comment, or anything you want to be displayed here.</h3>
<div id="commentForm">

<stripes:form name="comment" id="comment" action="/action/Comment">
<table>
<tr>
<tr>
<td>Name*</td><td><stripes:text name="entryComment.name" id="entryComment.name" maxlength="50" size="30" value="" onfocus="javascript: new Ajax.Autocompleter(this, 'autocomplete_name', '/gathering/action/Entry?nameList', {paramName: 'value', minChars: 2, indicator: 'indicator1'});"/><span id="indicator1" style="display: none"><img src="/gathering/img/spinner.gif" alt="Working..." /></span>
	<div id="autocomplete_name" class="autocomplete"></div><stripes:errors field="entryComment.name" /></td>
</tr>
<tr>
<td>Content</td><td><stripes:text name="entryComment.comment"></stripes:text><stripes:errors field="entryComment.comment" /></td>
</tr>
<tr>
<tr>
<td colspan="2"><stripes:submit name="saveComment" value="Submit Comment"/></td>
</tr>
</table>
</stripes:form>
</div>
</div>
<div id="commentList">
<c:forEach items="${actionBean.commentList}" var="comment">
	<div class="commentHeader">
	Posted by ${comment.name} on <fmt:formatDate value="${comment.postDate}" pattern="MM/dd/yyyy"/> at <fmt:formatDate value="${comment.postDate}" pattern="hh:mm:ss"/>
	</div>
	<div class="commentBody">
	${comment.comment}
	</div>
</c:forEach>
</div>
</stripes:layout-component>
</stripes:layout-render>
