package com.example.Tech.Store.services;

import com.example.Tech.Store.dtos.ProductWishlist;
import com.example.Tech.Store.dtos.WishlistDto;
import com.example.Tech.Store.entities.Wishlist;
import com.example.Tech.Store.mappers.WishlistMapper;
import com.example.Tech.Store.repositories.ProductRepository;
import com.example.Tech.Store.repositories.WishlistRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class WishlistService {

    private final ProductRepository productRepository;
    private final AuthService authService;
    private final WishlistRepository wishlistRepository;
    private final WishlistMapper wishlistMapper;

    public void addProductWishlist(ProductWishlist productWishlist) {
        var product_id= productWishlist.getProductId();
        var product=productRepository.findById(product_id).orElseThrow(()->new RuntimeException("Product not found"));
        var user=authService.getLoggedUser();
        if(wishlistRepository.existsByUserIdAndProductId(user.getId(),product_id))
            throw new RuntimeException("Product wishlist already exists");

        var wishlist=new Wishlist();
        wishlist.setProduct(product);
        wishlist.setUser(user);
        wishlistRepository.save(wishlist);
    }

    public void removeProductWishlist(ProductWishlist productWishlist) {
        var product_id= productWishlist.getProductId();
        var product=productRepository.findById(product_id).orElseThrow(()->new RuntimeException("Product not found"));
        var user=authService.getLoggedUser();
        if(!wishlistRepository.existsByUserIdAndProductId(user.getId(),product_id))
            throw new RuntimeException("Product wishlist doesn't exists");


        wishlistRepository.deleteWishlistByProductIdAndUserId(product_id,user.getId());

    }

    public List<WishlistDto> getAllWishlists(){
        var user=authService.getLoggedUser();
        return wishlistRepository.findAllByUserId(user.getId())
                .stream()
                .map(wishList->  wishlistMapper.toDto(wishList))
                .toList();

    }

    public void getWishlist(WishlistDto wishlistDto) {
        if (!wishlistRepository.existsByUserIdAndProductId(wishlistDto.getUser_id(), wishlistDto.getProduct_id()))
            throw new RuntimeException("Wishlist doesn't exists");
    }
}
