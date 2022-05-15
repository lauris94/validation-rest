<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Validate work order</title>
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
    <script src="static/js/script.js" type="text/javascript"></script>
    <link href="static/css/common.css" rel="stylesheet" type="text/css">
</head>
<body>
<h2 class="centered">Work order validation</h2>
<div class="centered">
    <form>
        <span>WorkOrder json:</span>
        <div>
            <textarea id="content" rows="20" cols="100"></textarea>
        </div>
        <div class="centered button">
            <input type="button" id="validateBtn" value="Validate">
        </div>
    </form>
</div>
<div class="centered" id="result"></div>
</body>
</html>


