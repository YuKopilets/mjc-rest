package com.epam.esm.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import org.hibernate.envers.Audited;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * The {@code type User} is domain representation of <i>user_account</i> table.
 * It used for mapping to relational database.
 * The {@code orders} implements linking with table <i>user_order</i>.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see Entity
 */
@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_account")
@Inheritance(strategy = InheritanceType.JOINED)
@Audited
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Boolean active;

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "role", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;

    @ElementCollection(targetClass = RegistrationType.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "registration_type", joinColumns = @JoinColumn(name = "user_id"))
    @Column(name = "registration", nullable = false, length = 50)
    @Enumerated(EnumType.STRING)
    private Set<RegistrationType> registrationTypes;

    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    @OneToMany(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_account_id", referencedColumnName = "id")
    private List<Order> orders;

    public Set<UserRole> getRoles() {
        return Collections.unmodifiableSet(roles);
    }

    public Set<RegistrationType> getRegistrationTypes() {
        return Collections.unmodifiableSet(registrationTypes);
    }

    public List<Order> getOrders() {
        return Collections.unmodifiableList(orders);
    }
}
