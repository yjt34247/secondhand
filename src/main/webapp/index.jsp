<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>二手交易平台</title>
</head>
<body>
<h1>二手交易平台</h1>

<div>
    <c:choose>
        <c:when test="${not empty sessionScope.user}">
            <p>欢迎, ${sessionScope.user.username}!</p>
            <a href="${pageContext.request.contextPath}/user/logout">退出登录</a>
            <a href="${pageContext.request.contextPath}/item/publish">发布物品</a>
        </c:when>
        <c:otherwise>
            <a href="${pageContext.request.contextPath}/user/login.jsp">登录</a>
            <a href="${pageContext.request.contextPath}/user/register.jsp">注册</a>
        </c:otherwise>
    </c:choose>
</div>

<hr>

<c:if test="${requestScope.showItems}">
    <%-- 已登录时才显示搜索框和物品列表 --%>
    <div>
        <form action="${pageContext.request.contextPath}/search" method="get">
            <input type="text" name="keyword" placeholder="搜索二手物品..." />
            <button type="submit">搜索</button>
        </form>
    </div>

    <div>
        <h2>最新二手物品</h2>

        <c:if test="${empty requestScope.items}">
            <p>暂无二手物品</p>
        </c:if>

        <c:if test="${not empty requestScope.items}">
            <table border="1" cellpadding="10" cellspacing="0">
                <tr>
                    <th>标题</th>
                    <th>价格</th>
                    <th>分类</th>
                    <th>状态</th>
                    <th>发布者</th>
                    <th>发布时间</th>
                    <th>操作</th>
                </tr>
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
                            <c:if test="${sessionScope.user.id == item.userId}">
                                <a href="${pageContext.request.contextPath}/item/edit?id=${item.id}">编辑</a>
                            </c:if>
                        </td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
</c:if>

<c:if test="${not requestScope.showItems}">
    <%-- 未登录时的提示 --%>
    <div>
        <h2>${requestScope.message}</h2>
        <p>请先登录或注册账号，登录后可以：</p>
        <ul>
            <li>浏览所有二手物品</li>
            <li>搜索需要的物品</li>
            <li>发布自己的二手物品</li>
            <li>编辑和删除自己发布的物品</li>
        </ul>
    </div>
</c:if>

</body>
</html>