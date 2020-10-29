package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

public interface CrudDao<T> {
    T save(T entity);

    Optional<T> findById(Long id);

    List<T> findAll();

    void update(T entity);

    boolean delete(Long id);
}
