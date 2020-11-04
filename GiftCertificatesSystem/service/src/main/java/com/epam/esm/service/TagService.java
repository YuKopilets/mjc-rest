package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.DeleteByRequestedIdServiceException;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;
import com.epam.esm.service.exception.TagNotFoundServiceException;

import java.util.List;

public interface TagService {
    Tag addTag(Tag tag);

    Tag getTagById(Long id) throws TagNotFoundServiceException, InvalidRequestedIdServiceException;

    List<Tag> getAllTags();

    void removeTag(Long id) throws InvalidRequestedIdServiceException, DeleteByRequestedIdServiceException;

    void removeGiftCertificateTags(Long tagId) throws InvalidRequestedIdServiceException;
}
