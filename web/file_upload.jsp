<%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 21-Jun-23
  Time: 9:45 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<form method="post" enctype="multipart/form-data">
  <input type="file" name="file">
    <input type="text"  name="password" placeholder="default password" required>
  <button type="submit">Submit</button>
</form>
<a href="${pageContext.request.contextPath}/template">Download Template</a>
</body>
</html>
