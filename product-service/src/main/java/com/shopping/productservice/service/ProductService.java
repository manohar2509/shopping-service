package com.shopping.productservice.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shopping.productservice.dto.ProductRequest;
import com.shopping.productservice.dto.ProductResponse;
import com.shopping.productservice.model.Product;
import com.shopping.productservice.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest){
        Product product =   Product.builder()
                            .name(productRequest.getName())
                            .description(productRequest.getDescription())
                            .price(productRequest.getPrice())
                            .build();
        productRepository.save(product);
    }

    public ProductResponse mapToProductRespone(Product product){
            return ProductResponse.builder()
            .id(product.getId())
            .name(product.getName())
            .description(product.getDescription())
            .price(product.getPrice())            
            .build();
    }

    public List<ProductResponse> getAllProducts(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(p -> mapToProductRespone(p)).toList();

    }
    

    
}
