package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.DeleteByRequestedIdServiceException;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;
import com.epam.esm.service.exception.TagNotFoundServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;

    @Override
    public Tag addTag(Tag tag) {
        return tagDao.save(tag);
    }

    @Override
    public Tag getTagById(Long id) throws TagNotFoundServiceException, InvalidRequestedIdServiceException {
        validateId(id);
        return tagDao.findById(id).orElseThrow(() -> new TagNotFoundServiceException(
                "Tag with id=" + id + " not found!")
        );
    }

    @Override
    public List<Tag> getAllTags() {
        return tagDao.findAll();
    }

    @Override
    @Transactional
    public void removeTag(Long id) throws InvalidRequestedIdServiceException, DeleteByRequestedIdServiceException {
        validateId(id);
        if (tagDao.delete(id)) {
            removeGiftCertificateTags(id);
        } else {
            throw new DeleteByRequestedIdServiceException("Delete tag by requested id: " + id + " not completed");
        }
    }

    @Override
    public void removeGiftCertificateTags(Long tagId) throws InvalidRequestedIdServiceException {
        validateId(tagId);
        tagDao.deleteFromGiftCertificatesById(tagId);
    }

    private void validateId(Long id) throws InvalidRequestedIdServiceException {
        if (id <= 0) {
            throw new InvalidRequestedIdServiceException("Tag id: " + id
                    + " does not fit the allowed gap. Expected gap: id > 0");
        }
    }
}
