<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 21-Jun-23
  Time: 9:13 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Review Statistics</title>
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/tailwind.css">
    <!--Regular Datatables CSS-->
    <link href="https://cdn.datatables.net/1.10.19/css/jquery.dataTables.min.css" rel="stylesheet">
    <!--Responsive Extension Datatables CSS-->
    <link href="https://cdn.datatables.net/responsive/2.2.3/css/responsive.dataTables.min.css" rel="stylesheet">
    <jsp:include page="partials/dataTableCSS.jsp"></jsp:include>
</head>
<body>
<jsp:include page="partials/navbar.jsp"></jsp:include>
<div class="w-full max-w-full">
    <div class="relative flex flex-col min-w-0 break-words bg-white border-0 shadow-soft-xl rounded-2xl bg-clip-border">
        <div class="table-responsive">
            <table id="datatable-history" class="stripe hover" style="width:100%; padding-top: 1em;  padding-bottom: 1em;">
                <thead>
                <tr>
                    <th data-priority="1">#</th>
                    <th data-priority="2">Question</th>
                    <th data-priority="3">Correct Answers</th>
                    <th data-priority="4">Your Choice</th>
                    <th data-priority="5">Scores</th>
                    <th data-priority="6">Rating</th>
                </tr>
                </thead>
                <tbody>

                </tbody>

            </table>

        </div>
    </div>
</div>
</body>
<!-- jQuery -->
<script type="text/javascript" src="https://code.jquery.com/jquery-3.4.1.min.js"></script>

<!--Datatables -->
<script src="https://cdn.datatables.net/1.10.19/js/jquery.dataTables.min.js"></script>
<script src="https://cdn.datatables.net/responsive/2.2.3/js/dataTables.responsive.min.js"></script>
<script>

    $(document).ready(function () {

        var table = $('#datatable-history').DataTable({
            responsive: true
        })
            .columns.adjust()
            .responsive.recalc();
    });

</script>
</html>
