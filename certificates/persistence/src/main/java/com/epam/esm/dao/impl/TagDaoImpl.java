package com.epam.esm.dao.impl;

import com.epam.esm.dao.TagDao;
import com.epam.esm.dao.mapper.TagMapper;
import com.epam.esm.entity.Tag;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import javax.sql.DataSource;
import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

public class TagDaoImpl implements TagDao {

    private static final String INSERT_TAG = "INSERT INTO tag (name) VALUES (?)";
    private static final String SELECT_TAG = "SELECT (id, name) FROM tag WHERE id = ?";
    private static final String SELECT_ALL_TAGS = "SELECT (id, name) FROM tag";
    private static final String DELETE_TAG = "DELETE FROM tag WHERE id = ?";

    private final JdbcTemplate jdbcTemplate;
    private final TagMapper tagMapper;
    //private final KeyHolder keyHolder;

    public TagDaoImpl(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
        tagMapper = new TagMapper();
        //keyHolder = new GeneratedKeyHolder();
    }

    @Override
    public void save(Tag tag) {
        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(INSERT_TAG);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);
        //tag.setId((Long)keyHolder.getKeys().get("id"));
    }

    @Override
    public Optional<Tag> findTagById(Long id) {
        Optional<Tag> tag = Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_TAG, tagMapper, id));
        return tag;
    }

    @Override
    public List<Tag> getAllTags() {
        List<Tag> tags = jdbcTemplate.query(SELECT_ALL_TAGS, tagMapper);
        return tags;
    }

    @Override
    public void delete(Long id) {
        jdbcTemplate.update(DELETE_TAG, id);
    }

    @Override
    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
}
