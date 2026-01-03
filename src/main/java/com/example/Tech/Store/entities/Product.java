package com.example.Tech.Store.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.UuidGenerator;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue
    @UuidGenerator
    @Column(name = "id")
    private UUID id;

    @Column(name = "description", length = Integer.MAX_VALUE)
    private String description;



    @Column(name = "stock")
    private Short stock;


    @Column(name = "title")
    private String title;

    @Column(name = "cover_url")
    private String coverUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "category_id")
    private Category category;

    @Column(name="price")
    private Integer price;

    public void setCategory(Category category) {
        this.category = category;
        category.addProduct(this);
    }
    public void removeCategory(Category category) {
        this.category=null;
        category.removeProduct(this);
    }




}