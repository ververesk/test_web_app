package org.grigorovich.test_web_app.controllers;

import org.grigorovich.test_web_app.model.Expense;
import org.grigorovich.test_web_app.repository.ExpenseRepository;
import org.grigorovich.test_web_app.repository.RepositoryFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@WebServlet("/createExpense")
public class ExpenseInsertController extends HttpServlet {
    private final ExpenseRepository repository = RepositoryFactory.getExpenseRepository();

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        getServletContext().getRequestDispatcher("/WEB-INF/view/expenseInsertView.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String name = (String) request.getParameter("name");
            LocalDate created_at = LocalDate.parse(request.getParameter("created_at"));
            BigDecimal amount = new BigDecimal(request.getParameter("amount"));
            Expense expense = new Expense(name, created_at, amount);
            repository.insert(expense);
            response.sendRedirect(request.getContextPath() + "/expenses");
        } catch (Exception ex) {
            getServletContext().getRequestDispatcher("/WEB-INF/view/expenseInsertView.jsp").forward(request, response);
        }
    }
}
