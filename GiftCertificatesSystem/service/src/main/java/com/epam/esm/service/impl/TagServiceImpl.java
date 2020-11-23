package com.epam.esm.service.impl;

import com.epam.esm.dao.PageRequest;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.exception.TagNotFoundServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;
import java.util.List;

/**
 * The type implementation of Tag service.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see TagService
 */
@Service
@Validated
@RequiredArgsConstructor
public class TagServiceImpl implements TagService {
    private final TagDao tagDao;

    @Override
    public Tag addTag(@Valid Tag tag) {
        return tagDao.save(tag);
    }

    @Override
    public Tag getTagById(Long id) throws TagNotFoundServiceException {
        return tagDao.findById(id).orElseThrow(() -> new TagNotFoundServiceException(id));
    }

    @Override
    public List<Tag> getAllTags(PageRequest pageRequest) {
        return tagDao.findAll(pageRequest);
    }

    @Override
    public Tag getMostWidelyUsedTag() {
        return tagDao.findMostWidelyUsedTag();
    }

    @Override
    @Transactional
    public void removeTag(Long id) throws TagNotFoundServiceException {
        tagDao.findById(id).orElseThrow(() -> new TagNotFoundServiceException(id));
        tagDao.deleteGiftCertificatesTag(id);
        tagDao.delete(id);
    }
}
