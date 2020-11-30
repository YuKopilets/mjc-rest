package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.envers.Audited;

import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Collections;
import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Entity
//@Table(name = "oauth_user_account")
//@Audited
public class OAuth2User {
    private Long id;
    private String login;
    private String email;
    private Set<UserRole> roles;

    public Set<UserRole> getRoles() {
        return Collections.unmodifiableSet(roles);
    }
}
