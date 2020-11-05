package com.epam.esm.dao.impl;

import com.epam.esm.context.TestConfig;
import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
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
@ActiveProfiles("test")
@Sql({"/drop_gift_certificates_system_schema.sql", "/create_gift_certificates_system_schema.sql"})
@Sql(scripts = {"/gift_certificates_system_inserts.sql"})
class TagDaoImplTest {
    @Autowired
    private TagDao tagDao;

    @ParameterizedTest
    @MethodSource("prepareTag")
    void saveTest(Tag tag) {
        tagDao.save(tag);
        Long expected = 7L;
        Long actual = tag.getId();
        assertEquals(expected, actual);
    }

    @Test
    void findTagByIdTest() {
        Optional<Tag> tagById = tagDao.findById(1L);
        String expected = "rest";
        String actual = tagById.get().getName();
        assertEquals(expected, actual);
    }

    @Test
    void findTagByIdNegativeTest() {
        Optional<Tag> tagById = tagDao.findById(7L);
        assertFalse(tagById.isPresent());
    }

    @ParameterizedTest
    @MethodSource("prepareExceptedTags")
    void findAllTagsTest(List<Tag> expectedTags) {
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
    void deleteTest() {
        boolean isDeleted = tagDao.delete(1L);
        assertTrue(isDeleted);
    }

    @Test
    void deleteNegativeTest() {
        boolean isDeleted = tagDao.delete(7L);
        assertFalse(isDeleted);
    }

    private static Arguments[] prepareTag() {
        Tag tag = Tag.builder()
                .name("test tag")
                .build();
        return new Arguments[]{Arguments.of(tag)};
    }

    private static Arguments[] prepareExceptedTags() {
        List<Tag> tags = new ArrayList<>();
        Tag rest = Tag.builder()
                .id(1L)
                .name("rest")
                .build();
        Tag spa = Tag.builder()
                .id(2L)
                .name("spa")
                .build();
        Tag holiday = Tag.builder()
                .id(3L)
                .name("holiday")
                .build();
        Tag feast = Tag.builder()
                .id(4L)
                .name("feast")
                .build();
        Tag sport = Tag.builder()
                .id(5L)
                .name("sport")
                .build();
        Tag tourism = Tag.builder()
                .id(6L)
                .name("tourism")
                .build();
        tags.add(rest);
        tags.add(spa);
        tags.add(holiday);
        tags.add(feast);
        tags.add(sport);
        tags.add(tourism);
        return new Arguments[]{Arguments.of(tags)};
    }
}