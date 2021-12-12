package org.grigorovich.test_web_app.repository;

import java.util.List;

public interface Repository<T> {
    List<T> findAll();

    T find(int id);

    T update(T entity);

    T insert(T entity);

    T remove(int id, T entity);
}
