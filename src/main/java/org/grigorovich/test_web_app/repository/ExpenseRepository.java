package org.grigorovich.test_web_app.repository;

import org.grigorovich.test_web_app.model.Expense;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ExpenseRepository extends Repository<Expense>{
    BigDecimal totalExpenses();
    Map<String, BigDecimal> highestSpendingCategory();
    List<Expense> findAllExpenseJoinCategory();
}
