package com.jor.controller;

import com.jor.entity.Product;
import com.jor.exception.ProductNotFoundException;
import com.jor.service.ProductService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@AllArgsConstructor
@RequestMapping("/product")
@CrossOrigin("*")
@Tag(name = "Product APIs")
public class ProductController {

    private final ProductService productService;

    @PostMapping(value = "/add", consumes = {"multipart/form-data"})
    public ResponseEntity<Product> addProduct(
            @RequestParam("productName") String productName,
            @RequestParam("price") Double price,
            @RequestParam("quantity") Integer quantity,
            @RequestParam("quantityType") String quantityType,
            @RequestParam("file") MultipartFile file) {


        Product product = new Product();
        product.setProductName(productName);
        product.setPrice(price);
        product.setQuantity(quantity);
        product.setQuantityType(quantityType);

        return ResponseEntity.ok(productService.addProduct(product, file));
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllProduct() {
        return ResponseEntity.ok(productService.getAllProduct());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getProduct(@PathVariable Long id) throws ProductNotFoundException {
        return ResponseEntity.ok(productService.getProduct(id));
    }

}
