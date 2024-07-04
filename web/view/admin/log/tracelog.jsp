<%-- 
    Document   : tracelog
    Created on : Jul 11, 2023, 6:45:37 PM
    Author     : Asus
--%>
<%@page import="entity.TraceLog, java.util.ArrayList, java.util.List, utility.Utility, dao.UserDAO" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script src="https://code.jquery.com/jquery-1.10.2.js"
        type="text/javascript">

</script>

<script>
    $(document).ready(() => {
        setTimeout(() => {
            $.ajax({
                type: 'POST',
                url: "trace-log",
                data: {
                    filter: $("#filter").val()
                },
                success: function (data) {
                    let x = data.split(";");
                    document.getElementById("table").innerHTML = "";
                    let tbl = document.getElementById("table");
                    
//                    document.getElementById("data-view").innerHTML = "";
//                    let dv = document.getElementById("data-view");
                    let dv = document.createElement("div");

                    x.forEach(e => {
                        try {
                        let je = JSON.parse(e.trim());
                                let div = document.createElement("div");
                                let tr = document.createElement("tr");
                                let td = document.createElement("td");
                                div.innerHTML = '<a href="/SWP/admin/users">'+je.actor+'</a><br>'
                                + '<p>'+(je.status == 0 ? je.lastLog : 'online')+'</p>';
                                td.append(div);
                                tr.append(td);
                                dv.append(tr);
                        } catch {

                        }
                    });
                    tbl.append(dv);
                }

            });
        });
    });
</script>

<script>
    $(document).ready(() => {
        $("#filter").change(() => {
            $.ajax({
                type: 'POST',
                url: "trace-log",
                data: {
                    filter: $("#filter").val()
                },
                success: function (data) {
                    let x = data.split(";");
                    document.getElementById("table").innerHTML = "";
                    let tbl = document.getElementById("table");
                    
//                    document.getElementById("data-view").innerHTML = "";
//                    let dv = document.getElementById("data-view");
                    let dv = document.createElement("div");

                    x.forEach(e => {
                        try {
                        let je = JSON.parse(e.trim());
                                let div = document.createElement("div");
                                let tr = document.createElement("tr");
                                let td = document.createElement("td");
                                div.innerHTML = '<a href="/SWP/admin/users">'+je.actor+'</a><br>'
                                + '<p>'+(je.status == 0 ? je.lastLog : 'online')+'</p>';
                                td.append(div);
                                tr.append(td);
                                dv.append(tr);
                        } catch {

                        }
                    });
                    tbl.append(dv);
                }

            });
        });
    });
</script>

<script>
    $(document).ready(() => {
        setInterval(() => {
            $.ajax({
                type: 'POST',
                url: "trace-log",
                data: {
                    filter: $("#filter").val()
                },
                success: function (data) {
                    let x = data.split(";");
                    document.getElementById("table").innerHTML = "";
                    let tbl = document.getElementById("table");
                    
//                    document.getElementById("data-view").innerHTML = "";
//                    let dv = document.getElementById("data-view");
                    let dv = document.createElement("div");

                    x.forEach(e => {
                        try {
                        let je = JSON.parse(e.trim());
                                let div = document.createElement("div");
                                let tr = document.createElement("tr");
                                let td = document.createElement("td");
                                div.innerHTML = '<a href="/SWP/admin/users">'+je.actor+'</a><br>'
                                + '<p>'+(je.status == 0 ? je.lastLog : 'online')+'</p>';
                                td.append(div);
                                tr.append(td);
                                dv.append(tr);
                        } catch {

                        }
                    });
                    tbl.append(dv);
                }

            });
        }, 6000);
    });
</script>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Trace Log</title>
    </head>
    <body>
        <div><h1>Trace Log</h1></div>
        <form>
            <div>
                <select id="filter" name="filter">
                    <option value="-1">(none)</option>
                    <option value="0">offline</option>
                    <option value="1">online</option>
                </select>
            </div>
        </form>

        <table border="1" id="table">
            
        </table>
    </body>
</html>
