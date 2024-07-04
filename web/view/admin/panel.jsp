<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 10-Jul-23
  Time: 9:11 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>Admin Panel</title>
  <meta name="author" content="David Grzyb">
  <meta name="description" content="">

  <!-- Tailwind -->
  <link href="${pageContext.request.contextPath}/css/tailwind.css" rel="stylesheet">
  <style>
    @import url('https://fonts.googleapis.com/css?family=Karla:400,700&display=swap');
    .font-family-karla { font-family: karla; }
    .bg-sidebar { background: #3d68ff; }
    .cta-btn { color: #3d68ff; }
    .upgrade-btn { background: #1947ee; }
    .upgrade-btn:hover { background: #0038fd; }
    .active-nav-link { background: #1947ee; }
    .nav-item:hover { background: #1947ee; }
    .account-link:hover { background: #3d68ff; }
  </style>
</head>
<body class="bg-gray-100 font-family-karla flex">


<div class="w-full flex flex-col h-screen overflow-y-hidden">
  <jsp:include page="../partials/navbar_admin.jsp"></jsp:include>

  <div class="w-full overflow-x-hidden border-t flex flex-col">
    <main class="w-full flex-grow p-6">
      <h1 class="text-3xl text-black pb-6">Dashboard</h1>
      <h2 class="text-2xl text-black pb-6">User</h2>
      <div class="py-3">
        <a class="px-4 py-3 text-white font-light tracking-wider bg-gray-900 rounded" href="${pageContext.request.contextPath}/admin/users">View List</a>
        <a class="px-4 py-3 text-white font-light tracking-wider bg-gray-900 rounded" href="${pageContext.request.contextPath}/admin/users/add">Add User</a>
      </div>
      <h2 class="text-2xl text-black pb-6">Class</h2>
      <div class="py-3">
        <a class="px-4 py-3 text-white font-light tracking-wider bg-gray-900 rounded" href="${pageContext.request.contextPath}/admin/classes">View List</a>
        <a class="px-4 py-3 text-white font-light tracking-wider bg-gray-900 rounded" href="${pageContext.request.contextPath}/admin/ShowCreateClassController">Add Class</a>
      </div>
      <h2 class="text-2xl text-black pb-6">Subject</h2>
      <div class="py-3">
        <a class="px-4 py-3 text-white font-light tracking-wider bg-gray-900 rounded" href="${pageContext.request.contextPath}/admin/subjects">View List</a>
        <a class="px-4 py-3 text-white font-light tracking-wider bg-gray-900 rounded" href="${pageContext.request.contextPath}/admin/subjects/add">Add Subject</a>
      </div>
      <h2 class="text-2xl text-black pb-6">Quiz</h2>
      <div class="py-3">
        <a class="px-4 py-3 text-white font-light tracking-wider bg-gray-900 rounded" href="${pageContext.request.contextPath}/admin/quizzes">View List</a>
      </div>
      <h2 class="text-2xl text-black pb-6">View List</h2>
      <div class="py-3">
        <a class="px-4 py-3 text-white font-light tracking-wider bg-gray-900 rounded" href="${pageContext.request.contextPath}/admin/bank">View Question Bank</a>
      </div>
      <h2 class="text-2xl text-black pb-6">Log</h2>
      <div class="py-3">
        <a class="px-4 py-3 text-white font-light tracking-wider bg-gray-900 rounded" href="${pageContext.request.contextPath}/admin/system-history">System History</a>
        <a class="px-4 py-3 text-white font-light tracking-wider bg-gray-900 rounded" href="${pageContext.request.contextPath}/admin/trace-log">Trace Log</a>
      </div>
      <h2 class="text-2xl text-black pb-6">Notfication</h2>
      <div class="py-3">
        <a class="px-4 py-3 text-white font-light tracking-wider bg-gray-900 rounded" href="${pageContext.request.contextPath}/admin/notification">View Notification</a>
      </div>
      

    </main>

  </div>

</div>

<!-- AlpineJS -->
<script src="https://cdn.jsdelivr.net/gh/alpinejs/alpine@v2.x.x/dist/alpine.min.js" defer></script>
<!-- Font Awesome -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/js/all.min.js" integrity="sha256-KzZiKy0DWYsnwMF+X1DvQngQ2/FxF7MF3Ff72XcpuPs=" crossorigin="anonymous"></script>
<!-- ChartJS -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/2.9.3/Chart.min.js" integrity="sha256-R4pqcOYV8lt7snxMQO/HSbVCFRPMdrhAFMH+vr9giYI=" crossorigin="anonymous"></script>

<script>
  var chartOne = document.getElementById('chartOne');
  var myChart = new Chart(chartOne, {
    type: 'bar',
    data: {
      labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
      datasets: [{
        label: '# of Votes',
        data: [12, 19, 3, 5, 2, 3],
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)',
          'rgba(54, 162, 235, 0.2)',
          'rgba(255, 206, 86, 0.2)',
          'rgba(75, 192, 192, 0.2)',
          'rgba(153, 102, 255, 0.2)',
          'rgba(255, 159, 64, 0.2)'
        ],
        borderColor: [
          'rgba(255, 99, 132, 1)',
          'rgba(54, 162, 235, 1)',
          'rgba(255, 206, 86, 1)',
          'rgba(75, 192, 192, 1)',
          'rgba(153, 102, 255, 1)',
          'rgba(255, 159, 64, 1)'
        ],
        borderWidth: 1
      }]
    },
    options: {
      scales: {
        yAxes: [{
          ticks: {
            beginAtZero: true
          }
        }]
      }
    }
  });

  var chartTwo = document.getElementById('chartTwo');
  var myLineChart = new Chart(chartTwo, {
    type: 'line',
    data: {
      labels: ['Red', 'Blue', 'Yellow', 'Green', 'Purple', 'Orange'],
      datasets: [{
        label: '# of Votes',
        data: [12, 19, 3, 5, 2, 3],
        backgroundColor: [
          'rgba(255, 99, 132, 0.2)',
          'rgba(54, 162, 235, 0.2)',
          'rgba(255, 206, 86, 0.2)',
          'rgba(75, 192, 192, 0.2)',
          'rgba(153, 102, 255, 0.2)',
          'rgba(255, 159, 64, 0.2)'
        ],
        borderColor: [
          'rgba(255, 99, 132, 1)',
          'rgba(54, 162, 235, 1)',
          'rgba(255, 206, 86, 1)',
          'rgba(75, 192, 192, 1)',
          'rgba(153, 102, 255, 1)',
          'rgba(255, 159, 64, 1)'
        ],
        borderWidth: 1
      }]
    },
    options: {
      scales: {
        yAxes: [{
          ticks: {
            beginAtZero: true
          }
        }]
      }
    }
  });
</script>
</body>
</html>
