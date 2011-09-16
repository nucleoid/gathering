<%@ include file="/WEB-INF/jsp/layout/taglibs.jsp" %>
<stripes:layout-render name="/WEB-INF/jsp/layout/default.jsp">
<stripes:layout-component name="html-head">
	<script type="text/javascript" src="/gathering/js/calendar1.js"></script>
	<script type="text/javascript" src="/gathering/js/calendar2.js"></script>
	<script type="text/javascript" src="/gathering/js/calendar3.js"></script>
	<script type="text/javascript" src="/gathering/js/prototype.js"></script>
	<script type="text/javascript" src="/gathering/js/scriptaculous.js"></script>
	<script type="text/javascript" src="/gathering/js/controls.js"></script>
</stripes:layout-component>
<stripes:layout-component name="entryPage">active</stripes:layout-component>
<stripes:layout-component name="contents">
*Items selected in the dropdown override anything typed in the text box.<br />
**Autocompletes your name if you've previously submitted anything.
<div id="forms">
<div id="itemEntry">
<stripes:errors beanclass="statz.controller.ThingFormAction" />
<h3>Enter an item</h3>
<stripes:form name="thingForm" id="thingForm" action="/gathering/action/ThingForm" focus="entryThing.name">
<stripes:hidden name="entryThing.id" value="${actionBean.entryThing.id}" />
<table>
<tr>
<td>Name of item*</td><td><stripes:text name="entryThing.name" value="${actionBean.entryThing.name}"maxlength="50" size="30" /><stripes:errors field="entryThing.name"/></td>
</tr>
<tr>
<td>Owner**</td><td><stripes:text name="entryThing.owner" id="entryThing.owner" maxlength="50" size="30" value="${actionBean.entryThing.owner}" onfocus="javascript: new Ajax.Autocompleter(this, 'autocomplete_owner', '/gathering/action/Entry?nameList', {paramName: 'value', minChars: 2, indicator: 'indicator1'});"/><span id="indicator1" style="display: none"><img src="/gathering/img/spinner.gif" alt="Working..." /></span>
	<div id="autocomplete_owner" class="autocomplete"></div><stripes:errors field="entryThing.owner"/></td>
</tr>
<tr>
<td>Quantity</td><td><stripes:text name="entryThing.quantity" id="entryThing.quantity" value="${actionBean.entryThing.quantity}"/><stripes:errors field="entryThing.quantity"/></td>
</tr>
<tr>
<td>Type</td><td><stripes:select name="entryThing.type"><option value="${actionBean.entryThing.type.id}">${actionBean.entryThing.type.name}</option><stripes:options-collection collection="${actionBean.typeList}" value="id" label="name" /></stripes:select></td>
</tr>
<tr>
<td colspan="2"><stripes:submit name="saveThing" value="Submit Thing"/></td>
</tr>
</table>
</stripes:form>
</div>
</div>
</stripes:layout-component>
</stripes:layout-render>
