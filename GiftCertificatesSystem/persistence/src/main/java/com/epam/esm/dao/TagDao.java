package com.epam.esm.dao;

import com.epam.esm.entity.Tag;

import java.util.List;

public interface TagDao extends CrudDao<Tag> {
    List<Tag> findAllTagsByGiftCertificateId(Long id);

    void deleteGiftCertificateTagsByTagId(Long tagId);
}
