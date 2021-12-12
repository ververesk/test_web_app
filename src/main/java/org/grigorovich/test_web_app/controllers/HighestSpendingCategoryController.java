package org.grigorovich.test_web_app.controllers;

import org.grigorovich.test_web_app.repository.ExpenseRepository;
import org.grigorovich.test_web_app.repository.RepositoryFactory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/HSC")
public class HighestSpendingCategoryController extends HttpServlet {
    private final ExpenseRepository repository = RepositoryFactory.getExpenseRepository();

    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("hsc", repository.highestSpendingCategory());
        RequestDispatcher dispatcher = req.getServletContext()
                .getRequestDispatcher("/WEB-INF/view/highestSpendingCategoryView.jsp");
        dispatcher.forward(req, resp);
    }
}
