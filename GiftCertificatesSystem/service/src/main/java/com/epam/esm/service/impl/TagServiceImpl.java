package com.epam.esm.service.impl;

import com.epam.esm.dao.PageRequest;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.DeleteByRequestedIdServiceException;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;
import com.epam.esm.service.exception.PageNumberNotValidServiceException;
import com.epam.esm.service.exception.PageSizeNotValidServiceException;
import com.epam.esm.service.exception.TagNotFoundServiceException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type implementation of Tag service.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see TagService
 */
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
        return tagDao.findById(id).orElseThrow(() -> new TagNotFoundServiceException("Tag with id=" + id
                + " not found!")
        );
    }

    @Override
    public List<Tag> getAllTags(int page, int pageSize) throws PageNumberNotValidServiceException {
        validatePageNumber(page);
        validatePageSize(pageSize);
        PageRequest pageRequest = new PageRequest(page, pageSize);
        return tagDao.findAll(pageRequest);
    }

    @Override
    public void removeTag(Long id) throws InvalidRequestedIdServiceException, DeleteByRequestedIdServiceException {
        validateId(id);
        if (!tagDao.delete(id)) {
            throw new DeleteByRequestedIdServiceException("Delete tag by requested id: " + id + " not completed");
        }
    }

    private void validateId(Long id) throws InvalidRequestedIdServiceException {
        if (id <= 0) {
            throw new InvalidRequestedIdServiceException("Tag id: " + id
                    + " does not fit the allowed gap. Expected gap: id > 0");
        }
    }

    private void validatePageNumber(int page) {
        if (page < 0) {
            throw new PageNumberNotValidServiceException("Tags can't be load. " + page
                    + " is not valid value. Page must be positive number");
        }
    }

    private void validatePageSize(int size) {
        if (size < 0) {
            throw new PageSizeNotValidServiceException("Tags can't be load. " + size
                    + " is not valid value. Page must be positive number");
        }
    }
}
