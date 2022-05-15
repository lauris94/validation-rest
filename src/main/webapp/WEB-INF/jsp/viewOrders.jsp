<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Validation history</title>
    <link href="static/css/common.css" rel="stylesheet" type="text/css">
</head>
<body>
<div>
    <h2 class="centered">Work order validation requests history</h2>
    <table>
        <thead>
        <tr>
            <th>Timestamp</th>
            <th>Type</th>
            <th>Department</th>
            <th>Valid</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders}" var="order">
            <tr>
                <td>${order.timestamp}</td>
                <td>${order.type}</td>
                <td>${order.department}</td>
                <td>${order.valid}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
