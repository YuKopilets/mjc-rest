package com.epam.esm.entity;

import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "user_account")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String login;

    @OneToMany(cascade = CascadeType.REMOVE)
    @JoinColumn(name = "user_account_id", referencedColumnName = "id")
    @ToString.Exclude
    private List<Order> orders;
}
