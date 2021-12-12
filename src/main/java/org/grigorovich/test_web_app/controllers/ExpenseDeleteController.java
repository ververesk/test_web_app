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

@WebServlet("/deleteExpense")
public class ExpenseDeleteController extends HttpServlet {

        private final ExpenseRepository repository = RepositoryFactory.getExpenseRepository();

        protected void doGet(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {

            try {
                int id = Integer.parseInt(request.getParameter("id"));
                Expense expense = repository.find(id);
                repository.remove(id, expense); //
                response.sendRedirect(request.getContextPath() + "/expenses");
                /*
                выбрасывает на страницу notfound но все удаляет
                 */
            } catch (Exception ex) {
                getServletContext().getRequestDispatcher("/WEB-INF/view/notfound.jsp").forward(request, response);
            }
        }
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
                throws ServletException, IOException {
            doGet(request, response);
        }
    }

