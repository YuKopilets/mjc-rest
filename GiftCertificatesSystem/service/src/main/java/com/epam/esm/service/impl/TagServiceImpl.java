package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;
import com.epam.esm.service.exception.TagNotFoundServiceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Tag getTagById(Long id) throws TagNotFoundServiceException, InvalidRequestedIdServiceException {
        if (id > 0) {
            Optional<Tag> tagById = tagDao.findById(id);
            return tagById.orElseThrow(() -> new TagNotFoundServiceException("Tag with id=" + id + " not found!"));
        } else {
            throw new InvalidRequestedIdServiceException(id + " does not fit the allowed gap. Expected gap: 0 > id");
        }
    }

    @Override
    public List<Tag> getAllTags() {
        return tagDao.findAll();
    }

    @Override
    @Transactional
    public void removeTag(Long id) throws InvalidRequestedIdServiceException {
        if (id > 0) {
            tagDao.delete(id);
            removeGiftCertificateTags(id);
        } else {
            throw new InvalidRequestedIdServiceException(id + " does not fit the allowed gap. Expected gap: 0 > id");
        }
    }

    @Override
    public void removeGiftCertificateTags(Long tagId) throws InvalidRequestedIdServiceException {
        if (tagId > 0) {
            tagDao.deleteGiftCertificateTagsByTagId(tagId);
        } else {
            throw new InvalidRequestedIdServiceException(tagId + " does not fit the allowed gap. Expected gap: 0 > id");
        }
    }
}
