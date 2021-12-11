<%@ page language="java" contentType="text/html; charset=UTF-8"
         pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Create Expense</title>
</head>
<body>

<h3>Create Expense</h3>


<form method="POST" action="${pageContext.request.contextPath}/createExpense">
    <table border="0">
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
            <td colspan="2">
                <input type="submit" value="Submit" />
                <a href="expenses">Cancel</a>
            </td>
        </tr>
    </table>
</form>

</body>
</html>