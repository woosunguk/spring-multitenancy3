package com.example.springmultitenancy3.consumer.service;

import com.example.springmultitenancy3.consumer.entity.Product;
import com.example.springmultitenancy3.consumer.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
}
