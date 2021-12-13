<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Expenses</title>
</head>
<body>
<h3>Expenses</h3>

<table border="1" cellpadding="5" cellspacing="1" >
    <tr>
        <th>edit</th>
        <th>delete</th>
        <th>id</th>
        <th>name</th>
        <th>created_at</th>
        <th>category</th>
        <th>amount</th>
    </tr>


<c:forEach var="ex" items="${requestScope.expenseList}">
        <tr>
            <td>
                <a href="updateExpense?id=${ex.id}">Edit</a>
            </td>
            <td>
                <a href="deleteExpense?id=${ex.id}">Delete</a>
            </td>
            <td>"${ex.id}"</td>
            <td>"${ex.name}"</td>
            <td>"${ex.created_at}"</td>
            <td>"${ex.categoryInt}"</td>
            <td>"${ex.amount}"</td>
        </tr>
    </c:forEach>
</table>
<a href="createExpense" >Create Expense</a>
<br>
<br>
<a href="totalExpenses" >Total Expenses</a>
<br>
<br>
<a href="HSC" >Highest Spending Category</a>
<br>
<br>
<form action="${pageContext.request.contextPath}/queryCriteria" method="POST">
    Please select a category: <select name="category">
    <option>food</option>
    <option>clothes</option>
    <option>utilities payments</option>
    <option>entertainment</option>
    <option>communal payments</option>
    <option>education</option>
    <option>travel</option>
</select>
    <br><br>
    Please choose start date<input type="date" name="start_date" value="1980-01-01"/>
    <br><br>
    Please choose finish date<input type="date" name="finish_date" value="2100-01-01"/>
    <br><br>
    <input type="submit" value="Submit" />
</form>
</body>
</html>
