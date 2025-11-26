<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="/WEB-INF/tlds/setting" prefix="Setting" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <Setting:Setting/>
                </div>
                <script type="text/javascript">
                    function ChangeDiv(number) {
                        if (number === 1) {
                            document.getElementById("div_start_access").style.display = "none";
                            document.getElementById("div_start_calendar").style.display = "block";
                        } else {
                            document.getElementById("div_start_calendar").style.display = "none";
                            document.getElementById("div_start_access").style.display = "block";
                        }
                    }
                </script>
            </div>
        </div>
    </body>
</html>
