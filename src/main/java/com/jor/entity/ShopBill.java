package com.jor.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
public class ShopBill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopBillId;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;

    private Long productId;
    private Integer quantity;
    private Double rate;
    private Double total;

    private String invoiceNumber;
}
