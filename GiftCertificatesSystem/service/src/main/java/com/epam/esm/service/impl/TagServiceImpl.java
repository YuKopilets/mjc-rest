package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.ServiceException;
import org.apache.commons.lang3.math.NumberUtils;
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
    public void addGiftCertificateTag(String giftCertificateId, String tagId) throws ServiceException {
        if (NumberUtils.isCreatable(giftCertificateId) && NumberUtils.isCreatable(tagId)) {
            Long numGiftCertificateId = NumberUtils.createLong(giftCertificateId);
            Long numTagId = NumberUtils.createLong(tagId);
            if (numGiftCertificateId > 0 && giftCertificateId.length() < 21
                    && numTagId > 0 && tagId.length() < 21) {
                tagDao.saveGiftCertificateIdAndTagId(numGiftCertificateId, numTagId);
            } else {
                throw new ServiceException(numGiftCertificateId + " or " + numTagId +
                        " does not fit the allowed gap. Expected gap: 0 > id");
            }
        } else {
            throw new ServiceException(giftCertificateId + " or " + tagId + " isn't a number. Expected type: Long");
        }
    }

    @Override
    public Tag getTagById(String id) throws ServiceException {
        if (NumberUtils.isCreatable(id)) {
            Long numId = NumberUtils.createLong(id);
            if (numId > 0 && id.length() < 21) {
                Optional<Tag> tagById = tagDao.findById(numId);
                if (tagById.isPresent()) {
                    return tagById.get();
                } else {
                    throw new ServiceException("Tag with id=" + id + " not found!");
                }
            } else {
                throw new ServiceException(numId + " does not fit the allowed gap. Expected gap: 0 > id");
            }
        } else {
            throw new ServiceException(id + " isn't a number. Expected type: Long");
        }
    }

    @Override
    public List<Tag> getAllTags() {
        return tagDao.findAll();
    }

    @Override
    public List<Tag> getGiftCertificateTags(String giftCertificateId) throws ServiceException {
        if (NumberUtils.isCreatable(giftCertificateId)) {
            Long numGiftCertificateId = NumberUtils.createLong(giftCertificateId);
            if (numGiftCertificateId > 0 && giftCertificateId.length() < 21) {
                return tagDao.findAllTagsByGiftCertificateId(numGiftCertificateId);
            } else {
                throw new ServiceException(numGiftCertificateId + " does not fit the allowed gap. Expected gap: 0 > id");
            }
        } else {
            throw new ServiceException(giftCertificateId + " isn't a number. Expected type: Long");
        }
    }

    @Override
    public void removeTag(String id) throws ServiceException {
        if (NumberUtils.isCreatable(id)) {
            Long numId = NumberUtils.createLong(id);
            if (numId > 0 && id.length() < 21) {
                tagDao.delete(numId);
            } else {
                throw new ServiceException(numId + " does not fit the allowed gap. Expected gap: 0 > id");
            }
        } else {
            throw new ServiceException(id + " isn't a number. Expected type: Long");
        }
    }
}
