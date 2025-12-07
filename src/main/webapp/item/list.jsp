<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>二手物品列表</title>
</head>
<body>
<h1>二手物品列表</h1>

<div>
    <a href="${pageContext.request.contextPath}/index.jsp">返回首页</a>
    <c:if test="${not empty sessionScope.user}">
        <a href="${pageContext.request.contextPath}/item/publish">发布物品</a>
    </c:if>
</div>

<div>
    <form action="${pageContext.request.contextPath}/search" method="get">
        <input type="text" name="keyword" placeholder="搜索物品..." />
        <button type="submit">搜索</button>
    </form>
</div>

<c:if test="${empty requestScope.items}">
    <p>暂无二手物品</p>
</c:if>

<c:if test="${not empty requestScope.items}">
    <table border="1" cellpadding="10" cellspacing="0">
        <thead>
        <tr>
            <th>标题</th>
            <th>价格</th>
            <th>分类</th>
            <th>状态</th>
            <th>发布者</th>
            <th>发布时间</th>
            <th>操作</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach var="item" items="${requestScope.items}">
            <tr>
                <td>${item.title}</td>
                <td>¥${item.price}</td>
                <td>${item.category}</td>
                <td>${item.status}</td>
                <td>${item.username}</td>
                <td>${item.createdAt}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/item/detail?id=${item.id}">查看详情</a>
                    <c:if test="${not empty sessionScope.user and sessionScope.user.id == item.userId}">
                        <a href="${pageContext.request.contextPath}/item/edit?id=${item.id}">编辑</a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>