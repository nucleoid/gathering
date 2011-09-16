<%@ include file="/WEB-INF/jsp/layout/taglibs.jsp" %>
<stripes:layout-render name="/WEB-INF/jsp/layout/default.jsp">
	<stripes:layout-component name="html-head">
	<script type="text/javascript" src="/gathering/js/jquery-1.2.6.min.js"></script>
	<script type="text/javascript" src="/gathering/js/jquery.jmesa.js"></script>
	<script type="text/javascript" src="/gathering/js/jmesa.js"></script>
	<script type="text/javascript">        
        function onInvokeAction(id) {
            createHiddenInputFieldsForLimitAndSubmit(id);
        }
    </script> 
	</stripes:layout-component>
	<stripes:layout-component name="listPage">active</stripes:layout-component>
    <stripes:layout-component name="contents">
<div id="listNotes">    
-To see more items, you may need to click on one of the green arrows or 
just increase the number of items listed from 15 to 50 or 100. <br />
-Table columns are sortable by clicking on the column names.<br />
-Table columns are filterable by typing in the text boxes above their respective names.<br />
ex. Typing &quot;open&quot; (without the quotes) in the text box above the Owner column name, you'll be able to see all remaining open items that <br />
need to be claimed by someone.
</div>
<div id="brought">
<stripes:useActionBean var="actionBean" binding="/action/List"/>
<form name="itemForm" id="itemForm" action="/gathering/action/List">
<h4>Click the links in the "Name" column to claim and/or edit the entry.</h4>
<h4>List of Items</h4>
    <jmesa:tableModel  id="thing" items="${actionBean.thingList}" var="thing" maxRows="100" >
        <jmesa:htmlTable>               
            <jmesa:htmlRow highlighter="false">    
				<jmesa:htmlColumn property="whenUsed" title="When Used" />
                <jmesa:htmlColumn property="name" title="Name">
					<!-- <a href="/gathering/action/Entry/${thing.id}">${thing.name}</a> -->${thing.name}
				</jmesa:htmlColumn>
                <jmesa:htmlColumn property="owner" title="Owner" />
                <jmesa:htmlColumn property="quantity" title="Quantity" />
				<jmesa:htmlColumn property="type.name" title="Type" />
            </jmesa:htmlRow>
        </jmesa:htmlTable> 
    </jmesa:tableModel>
</form>
</div>
<br />
</stripes:layout-component>
</stripes:layout-render>
