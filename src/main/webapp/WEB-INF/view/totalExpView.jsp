<%@ page import="java.math.BigDecimal" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Total Expenses</title>
</head>
<body>
<p>Total Expenses</p>
<%
    BigDecimal totalExp = (BigDecimal) request.getAttribute("totalExp");
    out.println(totalExp+" условных единиц");
%>
<br>
<a href="expenses" >Back</a>
</body>
</html>
