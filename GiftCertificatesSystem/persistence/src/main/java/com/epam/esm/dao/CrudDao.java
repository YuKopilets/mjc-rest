package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

/**
 * The common interface Crud dao with CRUD operations to database.
 *
 * @param <T> the entity type parameter
 * @author Yuriy Kopilets
 * @version 1.0
 */
public interface CrudDao<T> {
    /**
     * Save entity.
     *
     * @param entity the entity
     * @return the entity
     */
    T save(T entity);

    /**
     * Find entity by id.
     *
     * @param id the id
     * @return the optional entity
     */
    Optional<T> findById(Long id);

    /**
     * Find all list of entity. The {@code page number}
     * can show which part of list needed to return.
     *
     * @param page the page number
     * @return the list
     */
    List<T> findAll(int page);

    /**
     * Update entity.
     *
     * @param entity the entity
     * @return the updated entity
     */
    T update(T entity);

    /**
     * Delete entity.
     *
     * @param id the id
     * @return the entity has been deleted
     */
    boolean delete(Long id);
}
