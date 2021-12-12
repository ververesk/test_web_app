package org.grigorovich.test_web_app;

import org.grigorovich.test_web_app.model.Expense;
import org.grigorovich.test_web_app.repository.ExpenseRepository;
import org.grigorovich.test_web_app.repository.RepositoryFactory;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public class Runner {
    private static final ExpenseRepository repository = RepositoryFactory.getExpenseRepository();
    public static void main(String[] args) {
//        List<Expense> students = repository.findAll();
//        System.out.println(students);

        BigDecimal decimal=repository.totalExpenses();
        System.out.println(decimal);

        Map<String, BigDecimal> map = repository.highestSpendingCategory();
        System.out.println(map);

    }
}


