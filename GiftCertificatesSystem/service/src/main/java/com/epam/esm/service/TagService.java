package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.DeleteByRequestedIdServiceException;
import com.epam.esm.service.exception.TagNotFoundServiceException;

import java.util.List;

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
    Tag addTag(Tag tag);

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
     * @param page     the page number
     * @param pageSize the page size
     * @return the list of all exists tags
     */
    List<Tag> getAllTags(int page, int pageSize);

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
     * @throws DeleteByRequestedIdServiceException in case of {@code tag by
     * current id hasn't been deleted}
     */
    void removeTag(Long id) throws DeleteByRequestedIdServiceException;
}
