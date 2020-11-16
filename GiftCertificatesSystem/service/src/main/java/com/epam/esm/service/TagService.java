package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.*;

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
     * @throws InvalidRequestedIdServiceException in case of {@code id is not
     * valid to do operation}
     */
    Tag getTagById(Long id) throws TagNotFoundServiceException, InvalidRequestedIdServiceException;

    /**
     * Get list of all tags.
     *
     * @param page     the page number
     * @param pageSize the page size
     * @return the list of all exists tags
     * @throws PageNumberNotValidServiceException in case of {@code page
     *                                        number is not valid to get list}
     * @throws PageSizeNotValidServiceException   in case of {@code page size
     *                                            is not valid to get list}
     */
    List<Tag> getAllTags(int page, int pageSize) throws PageNumberNotValidServiceException,
            PageSizeNotValidServiceException;

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
     * @throws InvalidRequestedIdServiceException  in case of {@code id is not
     * valid to do operation}
     * @throws DeleteByRequestedIdServiceException in case of {@code tag by
     * current id hasn't been deleted}
     */
    void removeTag(Long id) throws InvalidRequestedIdServiceException, DeleteByRequestedIdServiceException;
}
