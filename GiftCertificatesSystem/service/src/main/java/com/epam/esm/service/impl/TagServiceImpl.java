package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.ServiceException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private final TagDao tagDao;

    public TagServiceImpl(TagDao tagDao) {
        this.tagDao = tagDao;
    }

    @Override
    public void addTag(String name) {
        Tag tag = new Tag();
        tag.setName(name);
        tagDao.save(tag);
    }

    @Override
    public void addGiftCertificateTag(Long giftCertificateId, Long tagId) {
        tagDao.saveGiftCertificateIdAndTagId(giftCertificateId, tagId);
    }

    @Override
    public Tag getTagById(Long id) throws ServiceException {
        Optional<Tag> tagById = tagDao.findById(id);
        if (tagById.isPresent()) {
            return tagById.get();
        } else {
            throw new ServiceException("Tag with id=" + id + " not found!");
        }
    }

    @Override
    public List<Tag> getAllTags() {
        return tagDao.findAll();
    }

    @Override
    public List<Tag> getGiftCertificateTags(Long giftCertificateId) {
        return tagDao.findAllTagsByGiftCertificateId(giftCertificateId);
    }

    @Override
    public void removeTag(Long id) {
        tagDao.delete(id);
    }
}
