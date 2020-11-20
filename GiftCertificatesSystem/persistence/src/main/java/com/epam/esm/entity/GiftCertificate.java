package com.epam.esm.entity;

import com.epam.esm.entity.converter.JsonDurationSerializer;
import com.epam.esm.entity.validator.ValidDaysDuration;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.envers.Audited;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The {@code type Gift certificate} is domain representation of
 * <i>gift_certificate</i> table.
 * It used for mapping to relational database.
 * The {@code tags} implements linking with table <i>tag</i> using an additional
 * table <i>gift_certificate_has_tag</i>.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see Entity
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "gift_certificate")
@Audited
public class GiftCertificate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Size(min = 6, max = 255)
    @Pattern(regexp = "^[\\w]+(\\s[\\w]+)*$", message = "{certificate.name.contain}")
    @Column(nullable = false, unique = true)
    private String name;

    @Size(min = 10)
    @Pattern(regexp = "^[\\w]+(\\s[\\w]+)*$", message = "{certificate.description.contain}")
    @Column(nullable = false, columnDefinition = "text")
    private String description;

    @PositiveOrZero
    @Digits(integer = 10, fraction = 2)
    @Column(nullable = false, columnDefinition = "decimal(10,2)")
    private BigDecimal price;

    @Column(name = "create_date", nullable = false)
    private LocalDateTime createDate;

    @Column(name = "last_update_date", nullable = false)
    private LocalDateTime lastUpdateDate;

    @ValidDaysDuration
    @JsonSerialize(using = JsonDurationSerializer.class)
    @Column(nullable = false)
    private Duration duration;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "gift_certificate_has_tag",
            joinColumns = @JoinColumn(name = "gift_certificate_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id")
    )
    private Set<Tag> tags;

    @JsonIgnore
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = "giftCertificates", fetch = FetchType.LAZY)
    private List<Order> orders;

    public Set<Tag> getTags() {
        return Collections.unmodifiableSet(tags);
    }

    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }
}
