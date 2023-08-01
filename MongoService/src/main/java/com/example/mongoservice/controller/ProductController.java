package com.example.mongoservice.controller;

import com.example.mongoservice.dto.ProductRequest;
import com.example.mongoservice.dto.ProductResponse;
import com.example.mongoservice.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {
private ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> findAllProducts(){
        return productService.findAll();

    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody  ProductRequest productRequest){

        productService.createProduct(productRequest);

    }


}
