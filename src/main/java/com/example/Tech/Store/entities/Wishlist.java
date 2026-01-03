package com.example.Tech.Store.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Getter
@Setter
@Entity
@Table(name = "wishlist")
public class Wishlist {
    @EmbeddedId
    private WishlistId id;

    @MapsId("userId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @MapsId("productId")
    @ManyToOne()
    @JoinColumn(name = "product_id")
    private Product product;


}