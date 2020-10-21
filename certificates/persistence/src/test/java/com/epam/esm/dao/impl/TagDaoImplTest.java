package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.entity.Tag;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;
import org.springframework.test.jdbc.JdbcTestUtils;

import static org.junit.jupiter.api.Assertions.*;

import javax.sql.DataSource;
import java.util.Optional;

class TagDaoImplTest {

    private TagDao tagDao;

    @BeforeEach
    void setUp() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .addScript("create_test_db.sql")
                .build();
        tagDao = new TagDaoImpl(dataSource);
    }

    @AfterEach
    void tearDown() {
        JdbcTestUtils.dropTables(tagDao.getJdbcTemplate(), "tag");
        tagDao = null;
    }

    @Test
    void save() {

    }

    @Test
    void findTagById() {
        Tag tag = new Tag();
        tag.setName("test name");
        tagDao.save(tag);
        Optional<Tag> tagById = tagDao.findTagById(1L);
        assertEquals("test name", tagById.get().getName());
    }

    @Test
    void getAllTags() {
    }

    @Test
    void delete() {
    }
}