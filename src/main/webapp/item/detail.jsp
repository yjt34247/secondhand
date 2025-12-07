<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%-- 验证是否登录 --%>
<c:if test="${empty sessionScope.user}">
    <c:redirect url="/user/login.jsp" />
</c:if>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>物品详情</title>
</head>
<body>
<h1>物品详情</h1>

<c:if test="${not empty requestScope.item}">
    <div>
        <p><strong>标题：</strong> ${requestScope.item.title}</p>
        <p><strong>价格：</strong> ¥${requestScope.item.price}</p>
        <p><strong>分类：</strong> ${requestScope.item.category}</p>
        <p><strong>状态：</strong> ${requestScope.item.status}</p>
        <p><strong>描述：</strong></p>
        <p>${requestScope.item.description}</p>
        <p><strong>发布者：</strong> ${requestScope.item.username}</p>
        <p><strong>发布时间：</strong> ${requestScope.item.createdAt}</p>
        <p><strong>最后更新：</strong> ${requestScope.item.updatedAt}</p>
    </div>

    <div>
        <a href="${pageContext.request.contextPath}/index">返回首页</a>
        <a href="${pageContext.request.contextPath}/search">返回列表</a>
        <c:if test="${not empty sessionScope.user and sessionScope.user.id == requestScope.item.userId}">
            <a href="${pageContext.request.contextPath}/item/edit?id=${requestScope.item.id}">编辑</a>
            <form action="${pageContext.request.contextPath}/item/delete" method="post"
                  style="display: inline;" onsubmit="return confirm('确定要删除吗？')">
                <input type="hidden" name="id" value="${requestScope.item.id}" />
                <button type="submit">删除</button>
            </form>
        </c:if>
    </div>
</c:if>

<c:if test="${empty requestScope.item}">
    <p>物品不存在</p>
    <a href="${pageContext.request.contextPath}/index">返回首页</a>
</c:if>
</body>
</html>