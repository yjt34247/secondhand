<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>编辑物品</title>
</head>
<body>
<h1>编辑物品</h1>

<c:if test="${not empty requestScope.error}">
    <p style="color: red;">${requestScope.error}</p>
</c:if>

<c:if test="${not empty requestScope.item}">
    <form action="${pageContext.request.contextPath}/item/update" method="post">
        <input type="hidden" name="id" value="${requestScope.item.id}" />

        <div>
            <label>标题：</label>
            <input type="text" name="title" value="${requestScope.item.title}" required />
        </div>
        <div>
            <label>价格：</label>
            <input type="number" name="price" step="0.01" min="0"
                   value="${requestScope.item.price}" required />
        </div>
        <div>
            <label>分类：</label>
            <select name="category">
                <option value="电子产品" ${requestScope.item.category == '电子产品' ? 'selected' : ''}>电子产品</option>
                <option value="家具家电" ${requestScope.item.category == '家具家电' ? 'selected' : ''}>家具家电</option>
                <option value="图书教材" ${requestScope.item.category == '图书教材' ? 'selected' : ''}>图书教材</option>
                <option value="服装鞋帽" ${requestScope.item.category == '服装鞋帽' ? 'selected' : ''}>服装鞋帽</option>
                <option value="运动器材" ${requestScope.item.category == '运动器材' ? 'selected' : ''}>运动器材</option>
                <option value="其他" ${requestScope.item.category == '其他' ? 'selected' : ''}>其他</option>
            </select>
        </div>
        <div>
            <label>状态：</label>
            <select name="status">
                <option value="在售" ${requestScope.item.status == '在售' ? 'selected' : ''}>在售</option>
                <option value="已售" ${requestScope.item.status == '已售' ? 'selected' : ''}>已售</option>
                <option value="下架" ${requestScope.item.status == '下架' ? 'selected' : ''}>下架</option>
            </select>
        </div>
        <div>
            <label>描述：</label><br>
            <textarea name="description" rows="5" cols="40">${requestScope.item.description}</textarea>
        </div>
        <div>
            <button type="submit">更新</button>
            <a href="${pageContext.request.contextPath}/item/detail?id=${requestScope.item.id}">取消</a>


        </div>
    </form>
</c:if>

<c:if test="${empty requestScope.item}">
    <p>物品不存在</p>
    <a href="${pageContext.request.contextPath}/index">返回首页</a>
</c:if>
</body>
</html>