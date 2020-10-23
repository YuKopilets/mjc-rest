package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.jdbc.JdbcTestUtils;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class TagDaoImplTest {
    private TagDao tagDao;
    private JdbcTemplate jdbcTemplate;

    @BeforeEach
    void setUp() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("create_tag_table.sql")
                .build();
        jdbcTemplate = new JdbcTemplate(dataSource);
        tagDao = new TagDaoImpl(jdbcTemplate);
    }

    @AfterEach
    void tearDown() {
        JdbcTestUtils.dropTables(jdbcTemplate, "tag");
        jdbcTemplate = null;
        tagDao = null;
    }

    @Test
    void savePositiveTest() {
        Tag tag = Tag.builder()
                .name("test name")
                .build();
        tagDao.save(tag);
        Long expected = 6L;
        Long actual = tag.getId();
        assertEquals(expected, actual);
    }

    @Test
    void saveNegativeTest() {
        Tag tag = Tag.builder()
                .name("test name")
                .build();
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

    private List<Tag> createExceptedTags() {
        ArrayList<Tag> tags = new ArrayList<>();
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