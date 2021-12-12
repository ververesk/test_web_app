package org.grigorovich.test_web_app.repository;

import lombok.extern.slf4j.Slf4j;
import org.grigorovich.test_web_app.exception.DatabaseException;
import org.grigorovich.test_web_app.model.Category;
import org.grigorovich.test_web_app.model.Expense;

import javax.sql.DataSource;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class ExpenseRepositoryPostgres implements ExpenseRepository {

    private static final String SELECT_FROM_EXPENSE =
            "select e.id id, e.name name, e.created_at created_at, e.category category, e.amount amount from expense e";
    private static final String SELECT_FROM_EXPENSE_AND_CATEGORY =
            "select e.id e_id, e.name e_name, created_at, c.id c_id, c.name c_name, amount " +
                    "from expense e join category c on e.category = c.id";
    private static final String ONE_ENTITY_FILTER = " where e.id = ?";
    private static final String FIND_EXPENSE_BY_ID = SELECT_FROM_EXPENSE + ONE_ENTITY_FILTER;
    private static final String DELETE_EXPENSE_BY_ID = "delete from expense e" + ONE_ENTITY_FILTER;
    private static final String INSERT_EXPENSE_SQL = "insert into expense (name, created_at, category, amount) values (?, ?, ?, ?) returning id";
    private static final String UPDATE_EXPENSE_SQL = "update expense e set name = ?, created_at = ?, amount = ?" + ONE_ENTITY_FILTER;
    private static final String TOTAL_EXPENSES = "select sum(e.amount) amount from expense e";
    private static final String HIGHEST_SPENDING_CATEGORY = "select c.name name, max(e.amount) amount from expense e join category c on (e.category=c.id)" +
            " GROUP BY c.name ORDER BY max(e.amount) desc limit 1";


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
    public BigDecimal totalExpenses() {
        BigDecimal totalExp = new BigDecimal(0);
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(TOTAL_EXPENSES);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                totalExp = rs.getBigDecimal("amount");
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseException(e);
        }
        return totalExp;
    }

    @Override
    public Map<String, BigDecimal> highestSpendingCategory() {
        Map<String, BigDecimal> highestSpendingCategory = new ConcurrentHashMap<>();
        try (Connection con = dataSource.getConnection();
             PreparedStatement ps = con.prepareStatement(HIGHEST_SPENDING_CATEGORY);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                highestSpendingCategory.put((
                        rs.getString("name")),
                        rs.getBigDecimal("amount")
                );
            }
        } catch (SQLException e) {
            log.error(e.getMessage());
            throw new DatabaseException(e);
        }
        return highestSpendingCategory;
    }

        @Override
        public List<Expense> findAllExpenseJoinCategory () {
            List<Expense> result;
            try (Connection con = dataSource.getConnection();
                 PreparedStatement ps = con.prepareStatement(SELECT_FROM_EXPENSE_AND_CATEGORY);
                 ResultSet rs = ps.executeQuery()) {
                result = resultSetToExpensesJoinCategory(rs);
            } catch (SQLException e) {
                log.error(e.getMessage());
                throw new DatabaseException(e);
            }
            return result;
        }

        @Override
        public List<Expense> findAll () {
            List<Expense> expenseList = new ArrayList<>();
            try (Connection con = dataSource.getConnection();
                 PreparedStatement ps = con.prepareStatement(SELECT_FROM_EXPENSE);
                 ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String name = rs.getString("name");
                    LocalDate created_at = rs.getDate("created_at").toLocalDate();
                    Integer categoryInt = rs.getInt("category");
                    BigDecimal amount = rs.getBigDecimal("amount");
                    Expense expense = new Expense();
                    expense.withId(id);
                    expense.withName(name);
                    expense.withCreated_at(created_at);
                    expense.withCategoryInt(categoryInt);
                    expense.withAmount(amount);
                    expenseList.add(expense);
                }
            } catch (SQLException e) {
                log.error(e.getMessage());
                throw new DatabaseException(e);
            }
            return expenseList;
        }


        private List<Expense> resultSetToExpensesJoinCategory (ResultSet rs) throws SQLException {
            Map<Integer, Expense> expenseMap = new ConcurrentHashMap<>();
            Map<Integer, Category> categoryMap = new ConcurrentHashMap<>();
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

//    private static <K, V> V putIfAbsentAndReturn(Map<K, V> map, K key, V value) {
//        if (key == null) {
//            return null;
//        }
//        map.putIfAbsent(key, value);
//        return map.get(key);
//    }

//    @Override
//    public Expense find(int id) {
//        List<Expense> result;
//        ResultSet rs = null;
//        try (Connection con = dataSource.getConnection();
//             PreparedStatement ps = con.prepareStatement(FIND_EXPENSE_BY_ID)) {
//            ps.setInt(1, id);
//            rs = ps.executeQuery();
//            result = resultSetToExpenses(rs);
//        } catch (SQLException e) {
//            log.error(e.getMessage());
//            throw new DatabaseException(e);
//        } finally {
//            closeQuietly(rs);
//        }
//        return result.get(0);
//    }

        @Override
        public Expense find ( int id){
            ResultSet rs = null;
            try (Connection con = dataSource.getConnection();
                 PreparedStatement ps = con.prepareStatement(FIND_EXPENSE_BY_ID)) {
                ps.setInt(1, id);
                rs = ps.executeQuery();
                while (rs.next()) {
                    int eId = rs.getInt("id");
                    String name = rs.getString("name");
                    LocalDate created_at = rs.getDate("created_at").toLocalDate();
                    Integer categoryInt = rs.getInt("category");
                    BigDecimal amount = rs.getBigDecimal("amount");
                    Expense expense = new Expense(eId, name, created_at, categoryInt, amount);
                    return expense;
                }
            } catch (SQLException e) {
                log.error(e.getMessage());

                throw new DatabaseException(e);
            } finally {
                closeQuietly(rs);
            }
            return null;
        }


        private static void closeQuietly (AutoCloseable closeable){
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
        public Expense update (Expense expense){
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
        public Expense insert (Expense expense){
            try (Connection con = dataSource.getConnection();
                 PreparedStatement ps = con.prepareStatement(INSERT_EXPENSE_SQL)) {
                ps.setString(1, expense.getName());
                ps.setDate(2, java.sql.Date.valueOf(expense.getCreated_at()));
                ps.setInt(3, expense.getCategoryInt());
                ps.setBigDecimal(4, expense.getAmount());
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
        public Expense remove ( int id, Expense expense){
            try (Connection con = dataSource.getConnection();
                 PreparedStatement ps = con.prepareStatement(DELETE_EXPENSE_BY_ID)) {
                ps.setInt(1, id);
                int affectedRows = ps.executeUpdate();
                if (affectedRows == 0) {
                    throw new SQLException("Creating user failed, no rows affected.");
                }
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
    }
