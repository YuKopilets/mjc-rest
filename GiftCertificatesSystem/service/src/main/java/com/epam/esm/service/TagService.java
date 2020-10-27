package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.ServiceException;

import java.util.List;

public interface TagService {
    void addTag(Tag tag);

    void addGiftCertificateTag(Long giftCertificateId, Long tagId) throws ServiceException;

    Tag getTagById(Long id) throws ServiceException;

    List<Tag> getAllTags();

    List<Tag> getGiftCertificateTags(Long giftCertificateId) throws ServiceException;

    void removeTag(Long id) throws ServiceException;
}
