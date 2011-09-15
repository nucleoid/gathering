<%@ include file="/WEB-INF/jsp/layout/taglibs.jsp" %>
<stripes:layout-render name="/WEB-INF/jsp/layout/default.jsp">
<stripes:layout-component name="surveyPage">active</stripes:layout-component>
<stripes:layout-component name="contents">
<div id="forms">
<stripes:errors globalErrorsOnly="true" />
<div id="surveyForm">
<c:choose>
<c:when test="${actionBean.success}">
Survey submitted successfully.  Thanks for taking the time to fill it out!
</c:when>
<c:otherwise>
<stripes:form name="survey" id="survey" focus="entryComment.name" beanclass="statz.controller.survey.SurveyAction">
Please give an answer for every question, but there is no requirement to do so.
<c:forEach items="${actionBean.questionList}" var="question" varStatus="loop">
	<div class="commentHeader">
	${loop.index+1}. ${question.detail}
	<c:choose>
	<c:when test="${not empty question.options}">
	<stripes:select name="response.answers[${loop.index}].content">
	</div>
	<div class="commentBody">
	<c:forEach items="${question.options}" var="option" >
	<stripes:option>${option.content}</stripes:option>
	</c:forEach>
	</stripes:select>
	Comment: <stripes:text name="response.answers[${loop.index}].comment" />
	</c:when>
	<c:otherwise>
	<stripes:text name="response.answers[${loop.index}].content" />
	</c:otherwise>
	</c:choose>
	<stripes:hidden name="response.answers[${loop.index}].question" value="${question}" />
	</div>
</c:forEach>
<br />
<stripes:submit name="saveAnswers" value="Submit Answers"/>
</stripes:form>
</c:otherwise>
</c:choose>
</div>
</div>
</stripes:layout-component>
</stripes:layout-render>