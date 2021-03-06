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
 * <i>local_account</i> table.
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
@Table(name = "local_account")
@Audited
public class LocalUser extends User {
    @Column(nullable = false, unique = true, length = 50)
    private String login;

    @Column(nullable = false)
    private char[] password;
}
