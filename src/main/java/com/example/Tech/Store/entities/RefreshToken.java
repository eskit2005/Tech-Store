package com.example.Tech.Store.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.Instant;

@Getter
@Setter
@Entity
@Table(name = "refresh_token")
public class RefreshToken {
    @Id
    @Column(name = "token")
    private String token;

    @NotNull
    @Column(name = "issuedat")
    private Instant issuedAt;

    @NotNull
    @Column(name = "expiry")
    private Instant expiry;

    @NotNull
    @Column(name = "revoked")
    private Boolean revoked;

    @NotNull
    @JoinColumn(name = "user_id")
    private long user_id;


}