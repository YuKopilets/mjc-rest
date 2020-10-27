package com.epam.esm.dao.impl;

import com.epam.esm.context.TestConfig;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
@Sql(scripts = {"classpath:create_tag_table.sql"})
class TagDaoImplTest {
    @Autowired
    private TagDao tagDao;

    @Test
    void savePositiveTest() {
        Tag tag = createTag();
        tagDao.save(tag);
        Long expected = 6L;
        Long actual = tag.getId();
        assertEquals(expected, actual);
    }

    @Test
    void saveNegativeTest() {
        Tag tag = createTag();
        tagDao.save(tag);
        Long expected = 2L;
        Long actual = tag.getId();
        assertNotEquals(expected, actual);
    }

    @Test
    void findTagByIdPositiveTest() {
        Optional<Tag> tagById = tagDao.findById(1L);
        String expected = "firstTag";
        String actual = tagById.get().getName();
        assertEquals(expected, actual);
    }

    @Test
    void findTagByIdNegativeTest() {
        Optional<Tag> tagById = tagDao.findById(6L);
        assertFalse(tagById.isPresent());
    }

    @Test
    void findAllTagsPositiveTest() {
        List<Tag> expectedTags = createExceptedTags();
        List<Tag> actualTags = tagDao.findAll();
        assertEquals(expectedTags, actualTags);
    }

    @Test
    void findAllTagsNegativeTest() {
        List<Tag> actualTags = tagDao.findAll();
        boolean isEmpty = actualTags.isEmpty();
        assertFalse(isEmpty);
    }

    @Test
    void deletePositiveTest() {
        boolean isDeleted = tagDao.delete(1L);
        assertTrue(isDeleted);
    }

    @Test
    void deleteNegativeTest() {
        boolean isDeleted = tagDao.delete(6L);
        assertFalse(isDeleted);
    }

    private Tag createTag() {
        return Tag.builder()
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