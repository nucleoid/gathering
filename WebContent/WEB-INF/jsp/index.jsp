<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/layout/taglibs.jsp" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
  <head><title>Statz gatherings</title></head>
  <body>
    <h1>Login</h1>
    <div id="explain">
    Use the username and password from the email Laura sent out. <br />
    This is just to keep out the riffraff.
	</div>
	<div id="login">
    <stripes:form action="/gathering/action/Index" focus="username">
    <stripes:errors />
        <table>
            <tr>
                <td>User Name:</td>
                <td><stripes:text name="username"/></td>
            </tr>
            <tr>
                <td>Password:</td>
                <td><stripes:password name="password"/></td>
            </tr>
            <tr>
                <td colspan="2">
                    <stripes:submit name="login" value="login"/>                    
                </td>
            </tr>
        </table>
    </stripes:form>
    </div>
  </body>
</html>
