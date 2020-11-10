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
     * Find list of all entities.
     *
     * @return the entities list
     */
    List<T> findAll();

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
