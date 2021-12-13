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
import java.util.List;

@WebServlet("/queryCriteria")
public class QueryCriteriaProcessorController extends HttpServlet {
    private final ExpenseRepository repository = RepositoryFactory.getExpenseRepository();

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String category = (String) request.getParameter("category");
            LocalDate startDate = LocalDate.parse(request.getParameter("start_date"));
            LocalDate finishDate = LocalDate.parse(request.getParameter("finish_date"));
            List<Expense> expenseList= repository.findAllExpenseJoinCategory(category, startDate, finishDate);
            request.setAttribute("expenseList", expenseList);
            getServletContext().getRequestDispatcher("/WEB-INF/view/queryCriteriaProcessorView.jsp").forward(request, response);
        } catch (Exception ex) {
            getServletContext().getRequestDispatcher("/WEB-INF/view/notfound.jsp").forward(request, response);
        }
    }
}
