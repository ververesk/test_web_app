package org.grigorovich.test_web_app.controllers;

import org.grigorovich.test_web_app.model.Expense;
import org.grigorovich.test_web_app.repository.ExpenseRepository;
import org.grigorovich.test_web_app.repository.RepositoryFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet(urlPatterns = {"/expenses"})
public class ExpenseListController extends HttpServlet {
    private final ExpenseRepository repository = RepositoryFactory.getExpenseRepository();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Expense> expenseList=repository.findAll();
        request.setAttribute("expenseList", expenseList);
        RequestDispatcher dispatcher = request.getServletContext()
                .getRequestDispatcher("/WEB-INF/view/expensesView.jsp");
        dispatcher.forward(request, response);
    }

}

