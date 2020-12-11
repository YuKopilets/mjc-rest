package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * The {@code type User} is domain representation of
 * <i>google_account</i> table.
 * It used for mapping to relational database.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see Entity
 */
@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "google_account")
@Audited
public class OAuth2GoogleUser extends User {
    @Column(nullable = false, unique = true)
    private String sub;

    @Column(nullable = false)
    private String name;

    private String email;

    @Column(name = "avatar_url")
    private String avatarUrl;
}
