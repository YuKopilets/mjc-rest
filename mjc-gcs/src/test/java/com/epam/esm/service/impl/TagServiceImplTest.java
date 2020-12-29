package com.epam.esm.service.impl;

import com.epam.esm.entity.Tag;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.TagNotFoundServiceException;
import com.epam.esm.persistence.repository.TagRepository;
import com.epam.esm.service.TagService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TagServiceImplTest {
    @Mock
    private TagRepository tagRepository;
    private TagService tagService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        tagService = Mockito.spy(new TagServiceImpl(tagRepository));
    }

    @AfterEach
    void tearDown() {
        tagRepository = null;
        tagService = null;
    }

    @ParameterizedTest
    @MethodSource("prepareTag")
    void addGiftCertificateTest(Tag tag) {
        tagService.addTag(tag);
        Mockito.verify(tagRepository).save(tag);
    }

    @ParameterizedTest
    @MethodSource("prepareTag")
    void getTagByIdTest(Tag tag) throws ServiceException {
        Optional<Tag> tagOptional = Optional.ofNullable(tag);
        Mockito.when(tagRepository.findById(1L)).thenReturn(tagOptional);
        tagService.getTagById(1L);
        Mockito.verify(tagRepository, Mockito.atLeastOnce()).findById(Mockito.anyLong());
    }

    @Test
    void getTagByIdNegativeTest() {
        Mockito.when(tagRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> tagService.getTagById(1L));
    }

    @ParameterizedTest
    @MethodSource("prepareExceptedTags")
    void getAllTagsTest(List<Tag> exceptedTags) {
        PageRequest pageRequest = PageRequest.of(1, 10);
        Mockito.when(tagRepository.findAll(Mockito.eq(pageRequest))).thenReturn(new PageImpl<>(exceptedTags));
        List<Tag> actualTags = tagService.getAllTags(pageRequest).getContent();
        assertEquals(exceptedTags, actualTags);
    }

    @ParameterizedTest
    @MethodSource("prepareExceptedTags")
    void getAllTagsNegativeTest(List<Tag> exceptedTags) {
        List<Tag> tags = new ArrayList<>();
        PageRequest pageRequest = PageRequest.of(1, 10);
        Mockito.when(tagRepository.findAll(Mockito.eq(pageRequest))).thenReturn(new PageImpl<>(tags));
        List<Tag> actualTags = tagService.getAllTags(pageRequest).getContent();
        assertNotEquals(exceptedTags, actualTags);
    }

    @Test
    void getMostWidelyUsedTagTest() {
        tagService.getMostWidelyUsedTag();
        Mockito.verify(tagRepository).findMostWidelyUsedTag();
    }

    @ParameterizedTest
    @MethodSource("prepareTag")
    void removeTagTest(Tag tag) throws ServiceException {
        Optional<Tag> tagOptional = Optional.of(tag);
        Mockito.when(tagRepository.findById(1L)).thenReturn(tagOptional);
        tagService.removeTag(1L);
        Mockito.verify(tagRepository).deleteById(Mockito.anyLong());
    }

    @Test
    void removeTagNegativeTest() {
        Mockito.when(tagRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(TagNotFoundServiceException.class, () -> tagService.removeTag(1L));
    }

    @Test
    void removeTagBadIdNegativeTest() {
        assertThrows(ServiceException.class, () -> tagService.removeTag(-1L));
    }

    private static Arguments[] prepareTag() {
        Tag tag = Tag.builder()
                .id(1L)
                .name("test tag")
                .build();
        return new Arguments[]{Arguments.of(tag)};
    }

    private static Arguments[] prepareExceptedTags() {
        List<Tag> tags = new ArrayList<>();
        Tag firstTag = Tag.builder()
                .id(1L)
                .name("firstTag")
                .build();
        Tag secondTag = Tag.builder()
                .id(2L)
                .name("secondTag")
                .build();
        Tag thirdTag = Tag.builder()
                .id(3L)
                .name("thirdTag")
                .build();
        Tag fourthTag = Tag.builder()
                .id(4L)
                .name("fourthTag")
                .build();
        Tag fifthTag = Tag.builder()
                .id(5L)
                .name("fifthTag")
                .build();
        tags.add(firstTag);
        tags.add(secondTag);
        tags.add(thirdTag);
        tags.add(fourthTag);
        tags.add(fifthTag);
        return new Arguments[]{Arguments.of(tags)};
    }
}