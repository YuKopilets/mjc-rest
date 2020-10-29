package com.epam.esm.controller;

import com.epam.esm.dto.GiftCertificateTagDto;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.ServiceException;
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
    public void createGiftCertificateTag(@RequestBody GiftCertificateTagDto dto) throws ServiceException {
        tagService.addGiftCertificateTag(dto.getGiftCertificateId(), dto.getTagId());
    }

    @GetMapping(value = "/{id}")
    public Tag getTagById(@PathVariable long id) throws ServiceException {
        return tagService.getTagById(id);
    }

    @GetMapping
    public List<Tag> getAllTags() {
        return tagService.getAllTags();
    }

    @GetMapping(value = "/certificate/{id}")
    public List<Tag> getTagByGiftCertificateId(@PathVariable long id) throws ServiceException {
        return tagService.getGiftCertificateTags(id);
    }

    @DeleteMapping(value = "/{id}")
    public void deleteTag(@PathVariable long id) throws ServiceException {
        tagService.removeTag(id);
    }
}
