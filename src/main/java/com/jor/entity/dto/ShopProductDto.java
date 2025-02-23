package com.jor.entity.dto;

import com.jor.entity.Product;
import com.jor.entity.Shop;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class ShopProductDto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopProductDtoId;
    private Long product;
    private Integer quantity;
    private String quantityType;
    private Double shopProductPrice;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

}
