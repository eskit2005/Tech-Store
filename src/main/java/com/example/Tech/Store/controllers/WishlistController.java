package com.example.Tech.Store.controllers;

import com.example.Tech.Store.dtos.ProductWishlist;
import com.example.Tech.Store.dtos.WishlistDto;
import com.example.Tech.Store.entities.Wishlist;
import com.example.Tech.Store.services.WishlistService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/wishlist")
public class WishlistController {

    private final WishlistService wishlistService;

    @PostMapping("/add")
    public ResponseEntity<Void> addProductWishlist(@Valid @RequestBody ProductWishlist productWishlist) {
        wishlistService.addProductWishlist(productWishlist);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("remove")
    public ResponseEntity<Void> removeProductWishlist(@Valid @RequestBody ProductWishlist productWishlist){
        wishlistService.removeProductWishlist(productWishlist);
        return ResponseEntity.ok().build();
    }

    @GetMapping("")
    public ResponseEntity<List<WishlistDto>> getAllWishlist(){
        return ResponseEntity.
                ok()
                .body(wishlistService.getAllWishlists());
    }

    @PostMapping("/checking")
    public ResponseEntity<Void> getWishlist(@Valid @RequestBody WishlistDto wishlistDto){
        wishlistService.getWishlist(wishlistDto);
        return ResponseEntity.ok().build();
    }

}
