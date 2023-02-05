package com.example.springmultitenancy3.consumer.repository;


import com.example.springmultitenancy3.consumer.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Integer> {
}
