package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

/**
 * The interface Crud dao.
 *
 * @param <T> the type parameter
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
     * Find by id.
     *
     * @param id the id
     * @return the optional
     */
    Optional<T> findById(Long id);

    /**
     * Find all list.
     *
     * @return the list
     */
    List<T> findAll();

    /**
     * Update entity.
     *
     * @param entity the entity
     * @return the entity
     */
    T update(T entity);

    /**
     * Delete boolean.
     *
     * @param id the id
     * @return the entity has been deleted
     */
    boolean delete(Long id);
}
