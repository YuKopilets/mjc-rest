package com.epam.esm.service.impl;

import com.epam.esm.service.TagService;
import com.epam.esm.service.context.TestConfig;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestConfig.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
@ActiveProfiles("test")
class TagServiceImplTest {
    @Autowired
    private TagService tagService;

    @Test
    void addTag() {
    }

    @Test
    void addGiftCertificateTag() {
    }

    @Test
    void getTagById() {
    }

    @Test
    void getAllTags() {
    }

    @Test
    void getGiftCertificateTags() {
    }

    @Test
    void removeTag() {
    }
}