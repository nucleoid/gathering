<%@ include file="/WEB-INF/jsp/layout/taglibs.jsp" %>
<stripes:layout-render name="/WEB-INF/jsp/layout/default.jsp">
	<stripes:layout-component name="html-head">
	<script type="text/javascript" src="/gathering/js/jmesa-min.js"></script>
	<script type="text/javascript" src="/gathering/js/jquery-1.2.6.min.js"></script>
	<script type="text/javascript">
		function onInvokeAction(id) {
        	setExportToLimit(id, '');
            createHiddenInputFieldsForLimitAndSubmit(id);
		}
        function onInvokeExportAction(id) {
            var parameterString = createParameterStringForLimit(id);
            location.href = '/gathering/action/Archive?' + parameterString;
        }
    </script>
	</stripes:layout-component>
	<stripes:layout-component name="archivePage">active</stripes:layout-component>
    <stripes:layout-component name="contents">
<h4>Items/Events/Comments From Last Year</h4>
<div id="brought">
<stripes:useActionBean var="actionBean" binding="/action/Archive"/>
<form name="itemForm" action="/gathering/action/Archive">
<jmesa:tableFacade  id="tag" items="${actionBean.thingList}" var="thing" maxRows="15" >
        <jmesa:htmlTable>               
            <jmesa:htmlRow highlighter="false">    
                <jmesa:htmlColumn property="name" title="Name" />
                <jmesa:htmlColumn property="owner" title="Owner" />
                <jmesa:htmlColumn property="quantity" title="Quantity" />
				<jmesa:htmlColumn property="type.name" title="Type" />
            </jmesa:htmlRow>
        </jmesa:htmlTable> 
    </jmesa:tableFacade>
</form>
</div>
<br />
<div id="schedule">
<form name="eventForm" action="/gathering/action/Archive">
<jmesa:tableFacade  id="tag" items="${actionBean.eventList}" var="event" maxRows="15" >
        <jmesa:htmlTable>               
            <jmesa:htmlRow highlighter="false">    
				<jmesa:htmlColumn property="whentime" title="When" pattern="EEE hh:mm aa" cellEditor="org.jmesa.view.editor.DateCellEditor" />
				<jmesa:htmlColumn property="name" title="Name" />
				<jmesa:htmlColumn property="location" title="Location" />
                <jmesa:htmlColumn property="duration" title="Duration" cellEditor="statz.util.EventDurationEditor" />
                <jmesa:htmlColumn property="facilitator" title="Facilitator" />	
            </jmesa:htmlRow>
        </jmesa:htmlTable> 
    </jmesa:tableFacade>
</form>
<br />
<br />
</div>
<div id="commentList">
Archived Comments: <br />
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