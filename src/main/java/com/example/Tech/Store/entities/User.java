package com.example.Tech.Store.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;


    @Column(name = "name")
    private String name;


    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

//    @OneToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE,CascadeType.REMOVE}, orphanRemoval = true)
//    private Profile profile;
}