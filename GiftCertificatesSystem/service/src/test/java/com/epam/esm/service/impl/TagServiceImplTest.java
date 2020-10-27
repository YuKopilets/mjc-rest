package com.epam.esm.service.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import com.epam.esm.service.TagService;
import com.epam.esm.service.context.TestConfig;
import com.epam.esm.service.exception.ServiceException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class TagServiceImplTest {
    @Autowired
    private TagDao tagDao;
    @Autowired
    private TagService tagService;

    @Test
    void addGiftCertificateTagPositiveTest() {
        assertDoesNotThrow(() -> tagService.addGiftCertificateTag(1L, 1L));
    }

    @Test
    void addGiftCertificateTagNegativeTest() {
        assertThrows(ServiceException.class, () -> tagService.addGiftCertificateTag(-1L, 1L));
    }

    @Test
    void getTagByIdPositiveTest() {
        Optional<Tag> tag = Optional.ofNullable(createTag());
        Mockito.when(tagDao.findById(1L)).thenReturn(tag);
        assertDoesNotThrow(() -> tagService.getTagById(1L));
    }

    @Test
    void getTagByIdNegativeTest() {
        Mockito.when(tagDao.findById(1L)).thenReturn(Optional.empty());
        assertThrows(ServiceException.class, () -> tagService.getTagById(1L));
    }

    @Test
    void getAllTagsPositiveTest() {
        List<Tag> exceptedTags = createExceptedTags();
        Mockito.when(tagDao.findAll()).thenReturn(exceptedTags);
        List<Tag> actualTags = tagService.getAllTags();
        assertEquals(exceptedTags, actualTags);
    }

    @Test
    void getAllTagsNegativeTest() {
        List<Tag> exceptedTags = createExceptedTags();
        List<Tag> tags = new ArrayList<>();
        Mockito.when(tagDao.findAll()).thenReturn(tags);
        List<Tag> actualTags = tagService.getAllTags();
        assertNotEquals(exceptedTags, actualTags);
    }

    @Test
    void getGiftCertificateTagsPositiveTest() throws ServiceException {
        List<Tag> exceptedTags = createExceptedTags();
        Mockito.when(tagDao.findAllTagsByGiftCertificateId(1L)).thenReturn(exceptedTags);
        List<Tag> actualTags = tagService.getGiftCertificateTags(1L);
        assertEquals(exceptedTags, actualTags);
    }

    @Test
    void getGiftCertificateTagsNegativeTest() {
        List<Tag> exceptedTags = createExceptedTags();
        Mockito.when(tagDao.findAllTagsByGiftCertificateId(-1L)).thenReturn(exceptedTags);
        assertThrows(ServiceException.class, () -> tagService.getGiftCertificateTags(-1L));
    }

    @Test
    void removeTagPositiveTest() {
        Mockito.when(tagDao.delete(1L)).thenReturn(true);
        assertDoesNotThrow(() -> tagService.removeTag(1L));
    }

    @Test
    void removeTagNegativeTest() {
        Mockito.when(tagDao.delete(-1L)).thenReturn(true);
        assertThrows(ServiceException.class, () -> tagService.removeTag(-1L));
    }

    private Tag createTag() {
        return Tag.builder()
                .id(1L)
                .name("test tag")
                .build();
    }

    private List<Tag> createExceptedTags() {
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
        return tags;
    }
}