package com.example.Tech.Store.entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id" )
    private Short id;


    @Column(name = "name")
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(fetch = FetchType.LAZY,cascade = {CascadeType.MERGE,CascadeType.REMOVE},orphanRemoval = true,mappedBy = "category")
    private Set<Product> products=new HashSet<>();

    public void addProduct(Product product) {
        products.add(product);

    }

    public void removeProduct(Product product) {
        products.remove(product);
    }

}