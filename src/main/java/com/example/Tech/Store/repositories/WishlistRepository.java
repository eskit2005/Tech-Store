package com.example.Tech.Store.repositories;

import com.example.Tech.Store.entities.Wishlist;
import com.example.Tech.Store.entities.WishlistId;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface WishlistRepository extends JpaRepository<Wishlist, WishlistId> {
    @Override
    Optional<Wishlist> findById(@NotNull WishlistId wishlistId);

    @Query("select (count(w) > 0) from Wishlist w where w.user.id = :userId and w.product.id = :productId")
    boolean existsByUserIdAndProductId(@Param("userId")Long userId, @Param("productId") UUID productId);

    void deleteWishlistByProductIdAndUserId(UUID productId, Long userId);

    @Query("From Wishlist w where w.user.id=:userId")
    List<Wishlist> findAllByUserId(@Param("userId")Long userId);
}
