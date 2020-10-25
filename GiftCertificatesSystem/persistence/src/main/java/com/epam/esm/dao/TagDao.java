package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagDao extends CrudDao<Tag> {
    void saveGiftCertificateIdAndTagId(Long giftCertificateId, Long tagId);

    List<Tag> findAllTagsByGiftCertificateId(Long id);
}
