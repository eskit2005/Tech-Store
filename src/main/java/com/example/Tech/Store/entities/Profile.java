package com.example.Tech.Store.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Getter
@Setter
@Entity
@Table(name = "profiles")
public class Profile {

    @Id
    @Column(name = "id")
    private Long id;

    @Column(name = "name")
    private String name;


    @Column(name = "bio")
    private String bio;

    @Column(name = "loyalty_points")
    private Integer loyaltyPoints;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="id")
    @MapsId
    private User user;




}