package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.exception.TagNotFoundServiceException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.Valid;

/**
 * The interface Tag service.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
public interface TagService {
    /**
     * Add new tag.
     *
     * @param tag the tag
     * @return the tag
     */
    Tag addTag(@Valid Tag tag);

    /**
     * Get single tag by id.
     *
     * @param id the tag id
     * @return the tag by id
     * @throws TagNotFoundServiceException        in case of {@code tag with
     * current id not found}
     */
    Tag getTagById(Long id) throws TagNotFoundServiceException;

    /**
     * Get list of all tags.
     *
     * @param pageable   the pageable
     * @return the list of all exists tags
     */
    Page<Tag> getAllTags(Pageable pageable);

    /**
     * Get most widely used tag of a user with the highest cost of all orders.
     *
     * @return the most widely used tag
     */
    Tag getMostWidelyUsedTag();

    /**
     * Remove tag.
     *
     * @param id the tag id
     * @throws TagNotFoundServiceException        in case of {@code tag with
     * current id not found}
     */
    void removeTag(Long id) throws TagNotFoundServiceException;
}
