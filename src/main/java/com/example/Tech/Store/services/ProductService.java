package com.example.Tech.Store.services;
import com.example.Tech.Store.dtos.AddProductRequest;
import com.example.Tech.Store.dtos.ProductDto;
import com.example.Tech.Store.entities.Product;
import com.example.Tech.Store.mappers.ProductMapper;
import com.example.Tech.Store.repositories.CategoryRepository;
import com.example.Tech.Store.repositories.ProductRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@AllArgsConstructor
@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final ProductMapper productMapper;

    @Transactional
    public ProductDto addProduct(AddProductRequest productRequest) {
        var category = categoryRepository.findById(productRequest.getCategoryId()).orElse(null);
        if(category == null) throw new RuntimeException("Category not found, product can't be added.Please provide a valid category id");
        var product = new Product();
        product.setDescription(productRequest.getDescription());
        product.setCategory(category);
        product.setStock(productRequest.getStock());
        product.setTitle(productRequest.getTitle());
        product.setCoverUrl(productRequest.getCover_url());
        product.setPrice(productRequest.getPrice());
        productRepository.save(product);
        return productMapper.toDto(product);
    }

    @Transactional
    public void removeProduct(String Product_name, UUID product_id) {
        var product = productRepository.findById(product_id).orElse(null);
        if (product == null)  throw new RuntimeException("Product not found");
        product.removeCategory(product.getCategory());
        productRepository.deleteById(product_id);
    }

    @Transactional
    public void updateProduct(ProductDto productDto) {
        var product=productRepository.findById(productDto.getId()).orElse(null);
        if (product == null) throw new RuntimeException("Product not found");
        var category=categoryRepository.findByName(productDto.getCategory_name()).orElse(null);
        if (category == null) throw new RuntimeException("Category not found");
        product.setDescription(productDto.getDescription());
        product.setCategory(category);
        product.setStock(productDto.getStock());
        product.setTitle(productDto.getTitle());
        product.setCoverUrl(productDto.getCoverUrl());
        product.setPrice(productDto.getPrice());
        productRepository.save(product);
    }



    @Transactional
    public Set<ProductDto> getAllProducts() {
        var products= productRepository.findAll()
                .stream()
                .sorted((p1, p2) -> p1.getTitle().compareTo(p2.getTitle()))
                .toList();
        var productDtos=new HashSet<ProductDto>();
        for(var product:products){
            productDtos.add(productMapper.toDto(product));
        }
        return productDtos;
    }

    @Transactional
    public ProductDto getProduct(UUID product_id) {
        var product = productRepository.findById(product_id).orElse(null);
        if (product == null)  throw new RuntimeException("Product not found");
        return productMapper.toDto(product);
    }
    @Transactional
    public ProductDto getProductByName(String product_title) {
        var product=productRepository.findProductByTitle(product_title).orElse(null);
        if (product == null)  throw new RuntimeException("Product not found");
        return productMapper.toDto(product);
    }

    @Transactional
    public Set<ProductDto> getProductByCategory(Short category_id) {
        var category=categoryRepository.findById(category_id).orElse(null);
        if(category == null) throw new RuntimeException("Category not found");
        var products=category.getProducts()
                .stream()
                .sorted((p1, p2) -> p1.getTitle().compareTo(p2.getTitle()))
                .toList();

        var productDtos=new HashSet<ProductDto>();
        for(var product:products){
            productDtos.add(productMapper.toDto(product));
        }
        return productDtos;
    }
}
