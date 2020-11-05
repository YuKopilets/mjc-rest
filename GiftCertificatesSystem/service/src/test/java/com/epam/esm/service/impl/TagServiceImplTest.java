package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.impl.TagDaoImpl;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.exception.ServiceException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TagServiceImplTest {
    @Mock
    private TagDao tagDao;
    private TagService tagService;

    @BeforeEach
    void setUp() {
        tagDao = Mockito.mock(TagDaoImpl.class);
        tagService = new TagServiceImpl(tagDao);
    }

    @AfterEach
    void tearDown() {
        tagDao = null;
        tagService = null;
    }

    @ParameterizedTest
    @MethodSource("prepareTag")
    void addGiftCertificateTest(Tag tag) {
        tagService.addTag(tag);
        Mockito.verify(tagDao).save(tag);
    }

    @ParameterizedTest
    @MethodSource("prepareTag")
    void getTagByIdTest(Tag tag) throws ServiceException {
        Optional<Tag> tagOptional = Optional.ofNullable(tag);
        Mockito.when(tagDao.findById(1L)).thenReturn(tagOptional);
        tagService.getTagById(1L);
        Mockito.verify(tagDao, Mockito.atLeastOnce()).findById(Mockito.anyLong());
    }

    @Test
    void getTagByIdNegativeTest() {
        Mockito.when(tagDao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> tagService.getTagById(1L));
    }

    @ParameterizedTest
    @MethodSource("prepareExceptedTags")
    void getAllTagsTest(List<Tag> exceptedTags) {
        Mockito.when(tagDao.findAll()).thenReturn(exceptedTags);
        List<Tag> actualTags = tagService.getAllTags();
        assertEquals(exceptedTags, actualTags);
    }

    @ParameterizedTest
    @MethodSource("prepareExceptedTags")
    void getAllTagsNegativeTest(List<Tag> exceptedTags) {
        List<Tag> tags = new ArrayList<>();
        Mockito.when(tagDao.findAll()).thenReturn(tags);
        List<Tag> actualTags = tagService.getAllTags();
        assertNotEquals(exceptedTags, actualTags);
    }

    @Test
    void removeTagTest() throws ServiceException {
        Mockito.when(tagDao.delete(1L)).thenReturn(true);
        tagService.removeTag(1L);
        Mockito.verify(tagDao).delete(Mockito.anyLong());
    }

    @Test
    void removeTagNegativeTest() {
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