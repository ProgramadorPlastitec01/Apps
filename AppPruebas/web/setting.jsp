<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/tld_setting.tld" prefix="Sett" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div class="main-wrapper main-wrapper-1">
            <jsp:include page="content.jsp"></jsp:include>
                <div class="main-content" style="min-height: 200px;">
                <Sett:Setting/>
            </div>
        </div>
    </body>
</html>
