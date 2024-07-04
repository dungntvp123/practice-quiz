<%-- 
    Document   : recorder.jsp
    Created on : Jul 11, 2023, 7:48:43 AM
    Author     : Asus
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>

<script src="https://code.jquery.com/jquery-1.10.2.js"
        type="text/javascript">

</script>

<script>
    $(document).ready(() => {
        setTimeout(() => {
            $.ajax({
                type: 'POST',
                url: "system-history",
                data: {
                    filter: $("#filter").val(),
                },
                success: function (result) {
                    let x = result.split(";");
                    document.getElementById("data-view").innerHTML = "";
                    let dv = document.getElementById("data-view");
                    x.forEach(e => {
                        try {
                        let je = JSON.parse(e.trim());
                        console.log(je.date);
                        let div = document.createElement("div");
                        div.innerHTML = '<p><span style="font-weight: bold">[' + je.date + ']</span> User <a href="/SWP/admin/users">' + je.actor + '</a> has <span>' + je.action + '</span> at table <a style="font-weight: bold">' + je.actionArea + '</a></p>';
                        dv.append(div);
                        } catch {
                            
                        }
                    });
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
                url: "system-history",
                data: {
                    filter: $("#filter").val(),
                },
                success: function (result) {
                    let x = result.split(";");
                    document.getElementById("data-view").innerHTML = "";
                    let dv = document.getElementById("data-view");
                    x.forEach(e => {
                        try {
                        let je = JSON.parse(e.trim());
                        console.log(je.date);
                        let div = document.createElement("div");
                        div.innerHTML = '<p><span style="font-weight: bold">[' + je.date + ']</span> User <a href="/SWP/admin/users">' + je.actor + '</a> has <span>' + je.action + '</span> at table <a style="font-weight: bold">' + je.actionArea + '</a></p>';
                        dv.append(div);
                        } catch {
                            
                        }
                    });
                }
            });
        }, 6000);
    });
</script>

<script>
    $(document).ready(() => {
        $("#filter").change(() => {
            $.ajax({
                type: 'POST',
                url: "system-history",
                data: {
                    filter: $("#filter").val(),
                },
                success: function (result) {
                    let x = result.split(";");
                    document.getElementById("data-view").innerHTML = "";
                    let dv = document.getElementById("data-view");
                    x.forEach(e => {
                        try {
                        let je = JSON.parse(e.trim());
                        console.log(je.date);
                        let div = document.createElement("div");
                        div.innerHTML = '<p><span style="font-weight: bold">[' + je.date + ']</span> User <a href="/SWP/admin/users">' + je.actor + '</a> has <span>' + je.action + '</span> at table <a style="font-weight: bold">' + je.actionArea + '</a></p>';
                        dv.append(div);
                        } catch {
                            
                        }
                    });
                }
            });
        });
    });
</script>

<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Recorder</title>
    </head>
    <body>
        
        <div><h1>System History</h1></div>
        <form>
            <div>
                <select id="filter" name="filter">
                    <option value="0">(none)</option>
                    <option value="1">Last 1h</option>
                    <option value="3">Last 3h</option>
                    <option value="8">Last 8h</option>
                    <option value="20">Last 20h</option>   
                </select>
            </div>
        </form>
        <div id="data-view">
        
        </div>
    </body>
</html>
