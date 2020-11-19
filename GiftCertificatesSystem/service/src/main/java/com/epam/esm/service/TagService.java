package com.epam.esm.service;

import com.epam.esm.dao.PageRequest;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.DeleteByRequestedIdServiceException;
import com.epam.esm.exception.TagNotFoundServiceException;

import javax.validation.Valid;
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
     * @param pageRequest the page number and size
     * @return the list of all exists tags
     */
    List<Tag> getAllTags(PageRequest pageRequest);

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
     * @throws TagNotFoundServiceException        in case of {@code tag with
     * current id not found}
     */
    void removeTag(Long id) throws TagNotFoundServiceException, DeleteByRequestedIdServiceException;
}
