package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;
import com.epam.esm.service.exception.TagNotFoundServiceException;

import java.util.List;

public interface TagService {
    Tag addTag(Tag tag);

    void addGiftCertificateTag(Long giftCertificateId, Long tagId) throws InvalidRequestedIdServiceException;

    Tag getTagById(Long id) throws TagNotFoundServiceException, InvalidRequestedIdServiceException;

    List<Tag> getAllTags();

    List<Tag> getGiftCertificateTags(Long giftCertificateId) throws InvalidRequestedIdServiceException;

    void removeTag(Long id) throws InvalidRequestedIdServiceException;
}
