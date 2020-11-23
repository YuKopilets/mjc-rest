package com.epam.esm.dao;

import java.util.List;
import java.util.Optional;

/**
 * The interface Crud dao.
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
     * Find all list of entity. The {@code page request}
     * can show which part of list needed to return.
     *
     * @param pageRequest the number and size of page
     * @return the list
     */
    List<T> findAll(PageRequest pageRequest);

    /**
     * Update entity.
     *
     * @param entity the entity
     * @return the updated entity
     */
    T update(T entity);

    /**
     * Delete entity by id.
     *
     * @param id the id
     */
    void delete(Long id);
}
