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
    public Tag addTag(Tag tag) {
        return tagDao.save(tag);
    }

    @Override
    public void addGiftCertificateTag(Long giftCertificateId, Long tagId) throws ServiceException {
        if (giftCertificateId > 0 && tagId > 0) {
            tagDao.saveGiftCertificateIdAndTagId(giftCertificateId, tagId);
        } else {
            throw new ServiceException(giftCertificateId + " or " + tagId +
                    " does not fit the allowed gap. Expected gap: 0 > id");
        }
    }

    @Override
    public Tag getTagById(Long id) throws ServiceException {
        if (id > 0) {
            Optional<Tag> tagById = tagDao.findById(id);
            if (tagById.isPresent()) {
                return tagById.get();
            } else {
                throw new ServiceException("Tag with id=" + id + " not found!");
            }
        } else {
            throw new ServiceException(id + " does not fit the allowed gap. Expected gap: 0 > id");
        }
    }

    @Override
    public List<Tag> getAllTags() {
        return tagDao.findAll();
    }

    @Override
    public List<Tag> getGiftCertificateTags(Long giftCertificateId) throws ServiceException {
        if (giftCertificateId > 0) {
            return tagDao.findAllTagsByGiftCertificateId(giftCertificateId);
        } else {
            throw new ServiceException(giftCertificateId + " does not fit the allowed gap. Expected gap: 0 > id");
        }
    }

    @Override
    public void removeTag(Long id) throws ServiceException {
        if (id > 0) {
            tagDao.delete(id);
        } else {
            throw new ServiceException(id + " does not fit the allowed gap. Expected gap: 0 > id");
        }
    }
}
