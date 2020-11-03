package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.Tag;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

@Repository
public class TagDaoImpl implements TagDao {
    private static final String INSERT_TAG = "INSERT INTO tag (name) VALUES (?)";
    private static final String SELECT_TAG = "SELECT id, name FROM tag WHERE id = ?";
    private static final String SELECT_ALL_TAGS = "SELECT id, name FROM tag";
    private static final String DELETE_TAG = "DELETE FROM tag WHERE id = ?";
    private static final String DELETE_GIFT_CERTIFICATE_TAGS = "DELETE FROM gift_certificate_has_tag WHERE tag_id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;

    public TagDaoImpl(JdbcTemplate jdbcTemplate, TagMapper tagMapper) {
        this.jdbcTemplate = jdbcTemplate;
        this.tagMapper = tagMapper;
    }

    @Override
    public Tag save(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_TAG, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);
        Optional.ofNullable(keyHolder.getKey()).map(Number::longValue).ifPresent(tag::setId);
        return tag;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        try {
            return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_TAG, tagMapper, id));
        } catch (EmptyResultDataAccessException e) {
            return Optional.empty();
        }
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SELECT_ALL_TAGS, tagMapper);
    }

    @Override
    public Tag update(Tag tag) {
        return tag;
    }

    @Override
    public boolean delete(Long id) {
        int updatedRows = jdbcTemplate.update(DELETE_TAG, id);
        return updatedRows > 0;
    }

    @Override
    public void deleteTagFromGiftCertificatesById(Long tagId) {
        jdbcTemplate.update(DELETE_GIFT_CERTIFICATE_TAGS, tagId);
    }
}
