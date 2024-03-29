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

@WebServlet("/updateExpense")
public class ExpenseUpdateController extends HttpServlet {
    private final ExpenseRepository repository = RepositoryFactory.getExpenseRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        Expense expense = repository.find(id);
        if (expense != null) {
            request.setAttribute("expense", expense);
            getServletContext().getRequestDispatcher("/WEB-INF/view/expenseUpdateView.jsp").forward(request, response);
        } else {
            getServletContext().getRequestDispatcher("/WEB-INF/view/notfound.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        String name = (String) request.getParameter("name");
        LocalDate created_at = LocalDate.parse(request.getParameter("created_at"));
        BigDecimal amount = new BigDecimal(request.getParameter("amount"));
        Expense expense = new Expense(id, name, created_at, amount);
        repository.update(expense);
        //getServletContext().getRequestDispatcher("/WEB-INF/view/expensesView.jsp").forward(request, response);
        response.sendRedirect(request.getContextPath() + "/expenses");
    }
}
