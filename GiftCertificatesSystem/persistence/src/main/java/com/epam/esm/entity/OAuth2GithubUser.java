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

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "github_account")
@Audited
public class OAuth2GithubUser extends User {
    @Column(nullable = false, unique = true)
    private String sub;

    @Column(nullable = false)
    private String login;

    private String email;

    @Column(name = "avatar_url")
    private String avatarUrl;
}