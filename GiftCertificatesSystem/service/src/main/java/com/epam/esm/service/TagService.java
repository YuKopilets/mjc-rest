package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.DeleteByRequestedIdServiceException;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;
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
     * @throws InvalidRequestedIdServiceException in case of {@code id is not
     * valid to do operation}
     */
    Tag getTagById(Long id) throws TagNotFoundServiceException, InvalidRequestedIdServiceException;

    /**
     * Get list of all tags.
     *
     * @return the list of all exists tags
     */
    List<Tag> getAllTags();

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

    /**
     * Remove tag in gift certificates.
     *
     * @param tagId the tag id
     * @throws InvalidRequestedIdServiceException in case of {@code id is not
     * valid to do operation}
     */
    void removeGiftCertificateTags(Long tagId) throws InvalidRequestedIdServiceException;
}
