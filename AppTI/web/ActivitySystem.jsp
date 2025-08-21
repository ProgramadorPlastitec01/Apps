<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib  uri="/WEB-INF/tlds/Tld_activitySystem.tld" prefix="ActivitySys" %>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
        <link rel="stylesheet" href="Interface/Content/Assets/css/activitysystem.css">
    </head>
    <body>
        <div id="app">
            <div class="main-wrapper main-wrapper-1">
                <jsp:include page="Menu.jsp"></jsp:include>
                    <div class="main-content" style="min-height: 694px;">
                    <ActivitySys:ActivitySystem/>
                </div>
            </div>
        </div>
        <script>
            function SendFormSelect(selectElement) {
                let selectedOption = selectElement.options[selectElement.selectedIndex];
                let year = selectedOption.getAttribute("data-year");
                let month = selectedOption.getAttribute("data-month");

                let form = document.createElement("form");
                form.method = "POST";
                form.action = "ActivitySystem?opt=1";

                let input = document.createElement("input");
                input.type = "hidden";
                input.name = "Data";     // nombre que recibes en el servlet
                input.value = year + "/" + month;

                form.appendChild(input);
                document.body.appendChild(form);
                form.submit();
            }

        </script>

    </body>
</html>
