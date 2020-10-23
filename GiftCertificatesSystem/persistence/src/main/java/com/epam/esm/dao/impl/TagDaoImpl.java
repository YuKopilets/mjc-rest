package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.Tag;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

public class TagDaoImpl implements TagDao {
    private static final String INSERT_TAG = "INSERT INTO tag (name) VALUES (?)";
    private static final String SELECT_TAG = "SELECT id, name FROM tag WHERE id = ?";
    private static final String SELECT_ALL_TAGS = "SELECT id, name FROM tag";
    private static final String DELETE_TAG = "DELETE FROM tag WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;
    private final KeyHolder keyHolder;

    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        tagMapper = new TagMapper();
        keyHolder = new GeneratedKeyHolder();
    }

    @Override
    public void save(Tag tag) {
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_TAG, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);
        tag.setId(keyHolder.getKey().longValue());
    }

    @Override
    public Optional<Tag> findById(Long id) {
        Tag tag;
        try {
            tag = jdbcTemplate.queryForObject(SELECT_TAG, tagMapper, id);
        } catch (EmptyResultDataAccessException e) {
            tag = null;
        }
        return Optional.ofNullable(tag);
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(SELECT_ALL_TAGS, tagMapper);
    }

    @Override
    public void update(Tag entity) {
    }

    @Override
    public boolean delete(Long id) {
        int updatedRows = jdbcTemplate.update(DELETE_TAG, id);
        return updatedRows > 0;
    }
}
