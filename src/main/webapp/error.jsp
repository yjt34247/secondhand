<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>错误页面</title>
</head>
<body>
<h1>错误</h1>

<c:if test="${not empty requestScope.error}">
    <p style="color: red;">${requestScope.error}</p>
</c:if>

<c:if test="${not empty sessionScope.error}">
    <p style="color: red;">${sessionScope.error}</p>
    <c:remove var="error" scope="session" />
</c:if>

<a href="${pageContext.request.contextPath}/index">返回首页</a>
</body>
</html>