<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:if test="${empty sessionScope.user}">
    <c:redirect url="/user/login.jsp" />
</c:if>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>搜索结果</title>
</head>
<body>
<h1>搜索结果</h1>

<div>
    <a href="${pageContext.request.contextPath}/index">返回首页</a>
</div>

<div>
    <form action="${pageContext.request.contextPath}/search" method="get">
        <input type="text" name="keyword" value="${requestScope.keyword}" placeholder="搜索物品..." />
        <button type="submit">搜索</button>
    </form>
</div>

<c:if test="${not empty requestScope.keyword}">
    <p>搜索关键词: "${requestScope.keyword}"</p>
</c:if>

<c:if test="${empty requestScope.items}">
    <p>没有找到相关物品</p>
</c:if>

<c:if test="${not empty requestScope.items}">
    <p>找到 ${requestScope.items.size()} 个物品</p>

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
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>