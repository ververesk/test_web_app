package org.grigorovich.test_web_app.repository;

import lombok.extern.slf4j.Slf4j;
import org.grigorovich.test_web_app.exception.DatabaseException;
import org.grigorovich.test_web_app.model.Category;
import org.grigorovich.test_web_app.model.Expense;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Slf4j
public class ExpenseRepositoryPostgres implements ExpenseRepository {

    private static final String SELECT_FROM_EXPENSE_AND_CATEGORY =
            "select e.id e_id, e.name e_name, created_at, c.id c_id, c.name c_name, amount " +
                    "from expense e join category c on e.category = c.id";
    private static final String ONE_ENTITY_FILTER = " where e.id = ?";
    private static final String FIND_EXPENSE_BY_ID = SELECT_FROM_EXPENSE_AND_CATEGORY + ONE_ENTITY_FILTER;
    private static final String DELETE_EXPENSE_BY_ID = "delete from expense e" + ONE_ENTITY_FILTER;
    private static final String INSERT_EXPENSE_SQL = "insert into expense (name, created_at, amount) values (?, ?, ?) returning id";
    private static final String UPDATE_EXPENSE_SQL = "update expense e set name = ?, created_at = ?, amount = ?" + ONE_ENTITY_FILTER;


    private final DataSource dataSource;
    private static volatile ExpenseRepositoryPostgres instance;

    private ExpenseRepositoryPostgres(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public static ExpenseRepositoryPostgres getInstance(DataSource dataSource) {
        if (instance == null) {
            synchronized (ExpenseRepositoryPostgres.class) {
                if (instance == null) {
                    instance = new ExpenseRepositoryPostgres(dataSource);
                }
            }
        }
        return instance;
    }

    @Override
    public List<Expense> findAll() {
        List<Expense> result;
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(SELECT_FROM_EXPENSE_AND_CATEGORY);
             ResultSet rs = ps.executeQuery()) {
            result = resultSetToExpenses(rs);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseException(e);
        }
        return result;
    }

    private List<Expense> resultSetToExpenses(ResultSet rs) throws SQLException {
        Map<Integer, Expense> expenseMap = new HashMap<>();
        Map<Integer, Category> categoryMap = new HashMap<>();
        while (rs.next()) {
            int eId = rs.getInt("e_id");
            int cId = rs.getInt("c_id");

            categoryMap.putIfAbsent(cId, new Category()
                   .withId(cId)
                   .withName(rs.getString("c_name")));

            expenseMap.putIfAbsent(eId,
                    new Expense()
                            .withId(eId)
                            .withName(rs.getString("e_name"))
                            .withCreated_at(rs.getDate("created_at").toLocalDate())
                            .withCategory(categoryMap.get(cId))
                            .withAmount(rs.getBigDecimal("amount")));
        }
        Collection<Expense> values = expenseMap.values();
        return values.isEmpty() ? new ArrayList<>() : new ArrayList<>(values);
    }

    private static <K, V> V putIfAbsentAndReturn(Map<K, V> map, K key, V value) {
        if (key == null) {
            return null;
        }
        map.putIfAbsent(key, value);
        return map.get(key);
    }

    @Override
    public Expense find(int id) {
        List<Expense> result;
        ResultSet rs = null;
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(FIND_EXPENSE_BY_ID)) {
            ps.setInt(1, id);
            rs = ps.executeQuery();
            result = resultSetToExpenses(rs);
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseException(e);
        } finally {
            closeQuietly(rs);
        }
        return result.get(0);
    }

    private static void closeQuietly(AutoCloseable closeable) {
        if (closeable == null) {
            return;
        }
        try {
            closeable.close();
        } catch (Exception e) {
            log.error("Couldn't close {}", closeable);
        }
    }

    @Override
    public Expense update(Expense expense) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(UPDATE_EXPENSE_SQL)) {
            ps.setString(1, expense.getName());
            ps.setDate(2, java.sql.Date.valueOf(expense.getCreated_at()));
            ps.setBigDecimal(3, expense.getAmount());
            ps.setInt(4, expense.getId());
            if (ps.executeUpdate() > 0) {
                return expense;
            }
            return null;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseException(e);
        }
    }

    @Override
    public Expense insert(Expense expense) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(INSERT_EXPENSE_SQL)) {
            ps.setString(1, expense.getName());
            ps.setDate(2, java.sql.Date.valueOf(expense.getCreated_at()));
            ps.setBigDecimal(3, expense.getAmount());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return expense.withId(rs.getInt(1));
            }
            return null;
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseException(e);
        }
    }

    @Override
    public void remove(int id) {
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(DELETE_EXPENSE_BY_ID)) {
            ps.setInt(1, id);
            int affectedRows = ps.executeUpdate();
            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }
            con.commit();
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseException(e);
        }
    }
}
