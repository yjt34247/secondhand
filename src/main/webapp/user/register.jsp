<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>用户注册</title>
</head>
<body>
<h1>用户注册</h1>

<c:if test="${not empty requestScope.error}">
    <p style="color: red;">${requestScope.error}</p>
</c:if>

<form action="${pageContext.request.contextPath}/user/register" method="post">
    <div>
        <label>用户名：</label>
        <input type="text" name="username" required />
    </div>
    <div>
        <label>密码：</label>
        <input type="password" name="password" required />
    </div>
    <div>
        <label>邮箱：</label>
        <input type="email" name="email" />
    </div>
    <div>
        <label>电话：</label>
        <input type="tel" name="phone" />
    </div>
    <div>
        <button type="submit">注册</button>
        <a href="${pageContext.request.contextPath}/user/login.jsp">已有账号？登录</a>
    </div>
</form>

<a href="${pageContext.request.contextPath}/index">返回首页</a>
</body>
</html>