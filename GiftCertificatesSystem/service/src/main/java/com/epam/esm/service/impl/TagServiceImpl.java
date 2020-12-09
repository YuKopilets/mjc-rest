package com.epam.esm.service.impl;

import com.epam.esm.repository.TagRepository;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.exception.TagNotFoundServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.validation.Valid;

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
    private final TagRepository tagRepository;

    @Override
    public Tag addTag(@Valid Tag tag) {
        return tagRepository.save(tag);
    }

    @Override
    public Tag getTagById(Long id) throws TagNotFoundServiceException {
        return tagRepository.findById(id).orElseThrow(() -> new TagNotFoundServiceException(id));
    }

    @Override
    public Page<Tag> getAllTags(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Override
    public Tag getMostWidelyUsedTag() {
        return tagRepository.findMostWidelyUsedTag();
    }

    @Override
    @Transactional
    public void removeTag(Long id) throws TagNotFoundServiceException {
        tagRepository.findById(id).orElseThrow(() -> new TagNotFoundServiceException(id));
        tagRepository.deleteById(id);
    }
}
