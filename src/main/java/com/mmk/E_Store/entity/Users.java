package com.mmk.E_Store.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity(name = "Users")
@Table(name = "Users",
        uniqueConstraints =
        @UniqueConstraint(
                name= "unique_email",
                columnNames = "email_address"
        ))
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Users {

    @Id
    @SequenceGenerator(
            name = "user_sequence",
            sequenceName = "user_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "user_sequence"
    )
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(
            name = "address",
            columnDefinition = "varchar",
            nullable = false
    )
    private String address;

    @Column(
            name = "email_address",
            columnDefinition = "varchar",
            nullable = false,
            unique = true
    )
    private String emailAddress;

    @Column(nullable = false)
    private String password;

    @OneToMany(mappedBy = "user")
    @JsonIgnore // Prevent circular reference
    private List<Orders> orders;
}