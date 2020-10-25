package com.epam.esm.service;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.ServiceException;

import java.util.List;

public interface TagService {
    void addTag(String name);

    void addGiftCertificateTag(Long giftCertificateId, Long tagId);

    Tag getTagById(Long id) throws ServiceException;

    List<Tag> getAllTags();

    List<Tag> getGiftCertificateTags(Long giftCertificateId);

    void removeTag(Long id);
}
