package com.example.mongoservice.service;

import com.example.mongoservice.dto.ProductRequest;
import com.example.mongoservice.dto.ProductResponse;
import com.example.mongoservice.model.Product;
import com.example.mongoservice.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@Slf4j
public class ProductService {
    private ProductRepository productRepository;
    @Autowired
    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public void createProduct(ProductRequest productRequest){
        Product product= Product.builder().
                name(productRequest.getName()).
                price(productRequest.getPrice()).
                build();
        productRepository.save(product);
        log.info("Product {} is saved",product.getId());
    }
    public List<ProductResponse> findAll(){
        List<Product> products =productRepository.findAll();
       return products.stream().map( this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product){
       return ProductResponse.builder().
                id(product.getId()).
                name(product.getName()).
                price(product.getPrice())
                .build();
    }


}
