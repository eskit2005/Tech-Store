package com.example.Tech.Store.controllers;
import com.example.Tech.Store.dtos.AddProductRequest;
import com.example.Tech.Store.dtos.CategoryDto;
import com.example.Tech.Store.dtos.ProductDto;
import com.example.Tech.Store.dtos.RemoveProductRequest;
import com.example.Tech.Store.services.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/product")
public class ProductController {
    private ProductService productService;

    @PostMapping("/add")
    public ResponseEntity<ProductDto> createProduct(@RequestBody AddProductRequest productRequest ){
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(productService.addProduct(productRequest));
    }

    @DeleteMapping("/remove")
    public ResponseEntity<Void> removeProduct(@RequestBody RemoveProductRequest productRequest ){
        productService.removeProduct(productRequest.getName(),productRequest.getId());
        return ResponseEntity
                .ok()
                .build();
    }

    @PutMapping("/update")
    public ResponseEntity<Void> updateProduct(@RequestBody ProductDto productDto){
        productService.updateProduct(productDto);
        return ResponseEntity
                .ok()
                .build();
    }

    @GetMapping("")
    public ResponseEntity<Set<ProductDto>> getAllProducts(){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getAllProducts());
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<ProductDto> getProductByID(@PathVariable UUID product_id){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getProduct(product_id));

    }

    @GetMapping("/name")
    public ResponseEntity<Set<ProductDto>> getProductByTitle(@RequestParam String product_title){
        return ResponseEntity
                .status(HttpStatus.OK)
                .body(productService.getProductByName(product_title));

    }

    @GetMapping("category")
    public ResponseEntity<Set<ProductDto>> getProductByCategory(@RequestParam Short Category_id){
        return ResponseEntity
                .ok()
                .body(productService.getProductByCategory(Category_id));
    }

    @GetMapping("/category/all")
    public ResponseEntity<Set<CategoryDto>> getCategories(){
        return ResponseEntity
                .ok()
                .body(productService.getAllCategories());

    }








}
