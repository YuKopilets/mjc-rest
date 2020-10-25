package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.ServiceException;

import java.util.List;

public interface TagService {
    void addTag(String name);

    void addGiftCertificateTag(String giftCertificateId, String tagId) throws ServiceException;

    Tag getTagById(String id) throws ServiceException;

    List<Tag> getAllTags();

    List<Tag> getGiftCertificateTags(String giftCertificateId) throws ServiceException;

    void removeTag(String id) throws ServiceException;
}
