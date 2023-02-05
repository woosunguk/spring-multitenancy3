package com.example.springmultitenancy3.tenant.service;

import com.example.springmultitenancy3.tenant.entity.Product;
import com.example.springmultitenancy3.tenant.repository.ProductRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Md. Amran Hossain
 */
@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    ProductRepository productRepository;

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }
}
