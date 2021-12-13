<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Expenses with category</title>
</head>
<body>
<h3>Expenses with category</h3>

<table border="1" cellpadding="5" cellspacing="1" >
    <tr>

        <th>name</th>
        <th>created_at</th>
        <th>category</th>
        <th>amount</th>
    </tr>


    <c:forEach var="ex" items="${requestScope.expenseList}">
        <tr>
            <td>"${ex.name}"</td>
            <td>"${ex.created_at}"</td>
            <td><c:out value="${empty ex.category ? '---' : ex.category.name}"/></td>
            <td>"${ex.amount}"</td>
        </tr>
    </c:forEach>
</table>
<a href="expenses" >Back</a>
<br>
<br>

</body>
</html>
