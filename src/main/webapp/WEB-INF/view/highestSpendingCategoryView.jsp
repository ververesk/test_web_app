<%@ page import="java.math.BigDecimal" %>
<%@ page import="java.util.Map" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Highest Spending Category</title>
</head>
<body>
<p>Highest Spending Category</p>
<%
    Map<String, BigDecimal> hsc = (Map<String, BigDecimal>) request.getAttribute("hsc");
    out.println(hsc+" условных единиц");
%>
<br>
<a href="expenses" >Back</a>
</body>
</html>
