package org.grigorovich.test_web_app.controllers;


import org.grigorovich.test_web_app.model.Expense;
import org.grigorovich.test_web_app.repository.ExpenseRepository;
import org.grigorovich.test_web_app.repository.RepositoryFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/api/expenses")
    public class ExpenseJsonController extends CommonJsonController {
        private static final String ID = "id";
        private ExpenseRepository repository = RepositoryFactory.getExpenseRepository();

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            String id = req.getParameter(ID);
            writeEntityToBody(id == null
                    ? repository.findAll()
                    : repository.find(Integer.parseInt(id)), resp);

        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            Expense expense = toEntity(Expense.class, req);
            writeEntityToBody(repository.insert(expense), resp);
        }

        @Override
        protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            Expense expense = toEntity(Expense.class, req);
            writeEntityToBody(repository.update(expense), resp);
        }

        @Override
        protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
            Expense expense = toEntity(Expense.class, req);
            String id = req.getParameter(ID);
            writeEntityToBody(repository.remove(Integer.parseInt(id), expense), resp);
        }
    }

