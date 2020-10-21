package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;
import java.util.Optional;

public interface TagDao extends Dao {
    void save(Tag tag);

    Optional<Tag> findTagById(Long id);

    List<Tag> getAllTags();

    void delete(Long id);
}
