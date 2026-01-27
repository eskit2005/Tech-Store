package com.example.Tech.Store.repositories;
import com.example.Tech.Store.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CategoryRepository extends JpaRepository<Category, Short> {

    @Query("Select c From Category c")
    @Override
    List<Category> findAll();

    Optional<Category> findById(Short categoryId);

    @Query("Select c from Category c where c.name= :name")
    Optional<Category> findByName(@Param("name") String name);

}
