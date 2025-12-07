<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>发布二手物品</title>
</head>
<body>
<h1>发布二手物品</h1>

<c:if test="${not empty requestScope.error}">
    <p style="color: red;">${requestScope.error}</p>
</c:if>

<form action="${pageContext.request.contextPath}/item/publish" method="post">
    <div>
        <label>标题：</label>
        <input type="text" name="title" required />
    </div>
    <div>
        <label>价格：</label>
        <input type="number" name="price" step="0.01" min="0" required />
    </div>
    <div>
        <label>分类：</label>
        <select name="category">
            <option value="电子产品">电子产品</option>
            <option value="家具家电">家具家电</option>
            <option value="图书教材">图书教材</option>
            <option value="服装鞋帽">服装鞋帽</option>
            <option value="运动器材">运动器材</option>
            <option value="其他">其他</option>
        </select>
    </div>
    <div>
        <label>描述：</label><br>
        <textarea name="description" rows="5" cols="40"></textarea>
    </div>
    <div>
        <button type="submit">发布</button>
        <a href="${pageContext.request.contextPath}/index">取消</a>
    </div>
</form>
</body>
</html>