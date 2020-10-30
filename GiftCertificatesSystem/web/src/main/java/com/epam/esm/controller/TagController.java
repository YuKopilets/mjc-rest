package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateTagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.exception.InvalidRequestedIdResponseStatusException;
import com.epam.esm.exception.TagNotFoundResponseStatusException;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;
import com.epam.esm.service.exception.TagNotFoundServiceException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagService tagService;

    public TagController(TagService tagService) {
        this.tagService = tagService;
    }

    @PostMapping
    public Tag createTag(@RequestBody Tag tag) {
        return tagService.addTag(tag);
    }

    @PostMapping(value = "/certificate")
    public void createGiftCertificateTag(@RequestBody GiftCertificateTagDto dto) {
        try {
            tagService.addGiftCertificateTag(dto.getGiftCertificateId(), dto.getTagId());
        } catch (InvalidRequestedIdServiceException e) {
            throw new InvalidRequestedIdResponseStatusException(e);
        }
    }

    @GetMapping(value = "/{id}")
    public Tag getTagById(@PathVariable long id) {
        try {
            return tagService.getTagById(id);
        } catch (TagNotFoundServiceException e) {
            throw new TagNotFoundResponseStatusException(e);
        } catch (InvalidRequestedIdServiceException e) {
            throw new InvalidRequestedIdResponseStatusException(e);
        }
    }

    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping(value = "/certificate/{id}")
    public List<Tag> getTagByGiftCertificateId(@PathVariable long id) {
        try {
            return tagService.getGiftCertificateTags(id);
        } catch (InvalidRequestedIdServiceException e) {
            throw new InvalidRequestedIdResponseStatusException(e);
        }
    }

    @DeleteMapping(value = "/{id}")
    public void deleteTag(@PathVariable long id) {
        try {
            tagService.removeTag(id);
        } catch (InvalidRequestedIdServiceException e) {
            throw new InvalidRequestedIdResponseStatusException(e);
        }
    }
}
