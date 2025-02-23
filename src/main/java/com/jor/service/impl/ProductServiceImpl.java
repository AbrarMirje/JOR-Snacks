package com.jor.service.impl;

import com.jor.entity.Product;
import com.jor.exception.ProductNotFoundException;
import com.jor.repository.ProductRepository;
import com.jor.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;
import software.amazon.awssdk.services.s3.model.PutObjectResponse;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    private final S3Client s3Client;

    @Value("${app.s3.bucket}")
    private String bucketName;
    @Override
    public Product addProduct(Product product, MultipartFile image) {

        String originalFilename = image.getOriginalFilename();
        String fileName = UUID.randomUUID().toString() + originalFilename.substring(originalFilename.lastIndexOf("."));

        try {
            // Upload file to S3
            PutObjectResponse putObjectResponse = s3Client.putObject(
                    PutObjectRequest.builder()
                            .bucket(bucketName)
                            .key(fileName)
                            .build(),
                    RequestBody.fromBytes(image.getBytes())
            );

            // Store S3 image URL in product entity
            String imageUrl = "https://" + bucketName + ".s3.amazonaws.com/" + fileName;
            product.setProductImage(imageUrl);

            product.setDate(LocalDate.now());
            return productRepository.save(product);

        } catch (IOException e) {
            throw new RuntimeException("Error uploading file to S3", e);
        }
    }

    @Override
    public List<Product> getAllProduct() {
        return productRepository.findAll();
    }

    @Override
    public Product getProduct(Long id) throws ProductNotFoundException {
        return productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product not found with id " + id)
        );
    }
}
