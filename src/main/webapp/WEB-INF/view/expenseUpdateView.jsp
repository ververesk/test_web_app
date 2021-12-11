<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Update Expense</title>
</head>
<body>


<h3>Update Expense</h3>

<c:if test="${not empty expense}">
    <form method="POST" action="${pageContext.request.contextPath}/updateExpense">
        <input type="hidden" name="id" value="${expense.id}" />
        <table border="0">
            <tr>
                <td>id</td>
                <td style="color:#002aff;">${expense.id}</td>
            </tr>
            <tr>
                <td>Name</td>
                <td><input type="text" name="name" value="${expense.name}" /></td>
            </tr>
            <tr>
                <td>Created_at</td>
                <td><input type="date" name="created_at" value="${expense.created_at}" /></td>
            </tr>
            <tr>
                <td>Amount</td>
                <td><input type="text" name="amount" value="${expense.amount}" /></td>
            </tr>
            <tr>
                <td colspan = "2">
                    <input type="submit" value="Submit" />
                    <a href="${pageContext.request.contextPath}/expenses">Cancel</a>
                </td>
            </tr>
        </table>
    </form>
</c:if>
</body>
</html>