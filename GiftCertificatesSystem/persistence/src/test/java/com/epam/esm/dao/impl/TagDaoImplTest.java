package com.epam.esm.dao.impl;

import com.epam.esm.context.TestConfig;
import com.epam.esm.entity.Tag;
import com.epam.esm.repository.TagRepository;
import org.apache.commons.lang3.StringUtils;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = TestConfig.class)
@ActiveProfiles("test")
@Sql({"/drop_gift_certificates_system_schema.sql", "/create_gift_certificates_system_schema.sql"})
@Sql(scripts = {"/gift_certificates_system_inserts.sql"})
class TagDaoImplTest {
    @Autowired
    private TagRepository tagRepository;

    @ParameterizedTest
    @MethodSource("prepareTag")
    void saveTest(Tag tag) {
        tagRepository.save(tag);
        Long expected = 7L;
        Long actual = tag.getId();
        assertEquals(expected, actual);
    }

    @Test
    void findTagByIdTest() {
        Optional<Tag> tagById = tagRepository.findById(1L);
        String expected = "rest";
        String actual = tagById.map(Tag::getName).orElse(StringUtils.EMPTY);
        assertEquals(expected, actual);
    }

    @Test
    void findTagByIdNegativeTest() {
        Optional<Tag> tagById = tagRepository.findById(7L);
        assertFalse(tagById.isPresent());
    }

    @ParameterizedTest
    @MethodSource("prepareExceptedTags")
    void findAllTagsTest(List<Tag> expectedTags) {
        PageRequest pageRequest = PageRequest.of(1, 6);
        List<Tag> actualTags = tagRepository.findAll(pageRequest).getContent();
        assertEquals(expectedTags, actualTags);
    }

    @ParameterizedTest
    @MethodSource("prepareExceptedTags")
    void findAllTagsNegativeTest(List<Tag> expectedTags) {
        PageRequest pageRequest = PageRequest.of(1, 3);
        List<Tag> actualTags = tagRepository.findAll(pageRequest).getContent();
        assertNotEquals(expectedTags, actualTags);
    }

    @Test
    void deleteTest() {
        tagRepository.deleteById(1L);
        Optional<Tag> tag = tagRepository.findById(1L);
        assertFalse(tag.isPresent());
    }

    private static Arguments[] prepareTag() {
        Tag tag = Tag.builder()
                .name("test_tag")
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