package com.jor.service;

import com.jor.entity.Product;
import com.jor.exception.ProductNotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface ProductService {
    Product addProduct(Product product, MultipartFile image);

    List<Product> getAllProduct();
    Product getProduct(Long id) throws ProductNotFoundException;
}
