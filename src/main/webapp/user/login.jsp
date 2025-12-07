<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户登录</title>
</head>
<body>
<h1>用户登录</h1>

<c:if test="${not empty requestScope.error}">
    <p style="color: red;">${requestScope.error}</p>
</c:if>

<form action="${pageContext.request.contextPath}/user/login" method="post">
    <div>
        <label>用户名：</label>
        <input type="text" name="username" required />
    </div>
    <div>
        <label>密码：</label>
        <input type="password" name="password" required />
    </div>
    <div>
        <button type="submit">登录</button>
        <a href="${pageContext.request.contextPath}/user/register.jsp">没有账号？注册</a>
    </div>
</form>

<a href="${pageContext.request.contextPath}/index">返回首页</a>
</body>
</html>