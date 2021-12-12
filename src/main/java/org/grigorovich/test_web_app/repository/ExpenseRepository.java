package org.grigorovich.test_web_app.repository;

import org.grigorovich.test_web_app.model.Expense;

import java.util.List;

public interface ExpenseRepository extends Repository<Expense>{
    List<Expense> findAllExpenseJoinCategory();
}
