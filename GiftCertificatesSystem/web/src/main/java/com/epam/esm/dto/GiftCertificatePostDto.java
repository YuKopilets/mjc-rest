package com.epam.esm.dto;

import com.epam.esm.entity.Tag;
import lombok.Value;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.Collections;
import java.util.Set;

/**
 * The {@code Gift certificate post dto} is dto for transferring gift
 * certificate data after request with <b>POST</b> http method.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@Value
public class GiftCertificatePostDto {
    @NotNull
    String name;

    @NotNull
    String description;

    @NotNull
    BigDecimal price;

    @NotNull
    Integer duration;

    @NotEmpty
    Set<Tag> tags;

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }
}
