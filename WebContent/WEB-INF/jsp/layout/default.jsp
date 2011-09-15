<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/WEB-INF/jsp/layout/taglibs.jsp" %>
<stripes:layout-definition>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
    <html xmlns="http://www.w3.org/1999/xhtml" xml:lang="en" lang="en">
        <head>
            <title>Christmas at Cragun's</title>
            <link rel="stylesheet" href="/gathering/css/default.css" type="text/css" />
            <link rel="stylesheet" href="/gathering/css/jmesa.css" type="text/css" />
            <!--[if lt IE 7]> 
				<link rel="stylesheet" href="/gathering/css/ie.css" type="text/css"> 
			<![endif]--> 
            <stripes:layout-component name="html-head"/>
        </head>
        <body>
        <div id="wrap">
        <div id="wrapper">
            <stripes:layout-component name="header">
                <jsp:include page="/WEB-INF/jsp/layout/header.jsp"/>
            </stripes:layout-component>
            
            <stripes:layout-component name="menu">
            	<stripes:layout-render name="/WEB-INF/jsp/layout/menu.jsp" />
            </stripes:layout-component>
            
            <div id="content-wrap">      
			<div id="content">
                <stripes:layout-component name="contents"/>
            </div>    
            </div>
		
            <stripes:layout-component name="footer">
                <jsp:include page="/WEB-INF/jsp/layout/footer.jsp"/>
            </stripes:layout-component>
        </div>
        </div>
        </body>
    </html>
</stripes:layout-definition>