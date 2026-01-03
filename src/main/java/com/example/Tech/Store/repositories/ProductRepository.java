package com.example.Tech.Store.repositories;

import com.example.Tech.Store.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {


    Optional<Product> findById(UUID id);

    Optional<Product> findProductByTitle(String title);

    @Query("Select p from Product p order by p.title desc")
    @Override
    List<Product> findAll();

}
