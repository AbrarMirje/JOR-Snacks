package com.jor.entity;

import com.jor.entity.dto.ShopProductDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long shopId;
    private String shopName;
    private String contact;

    @ManyToOne
    @JoinColumn(name = "location_id")
    private Location location;

    @OneToMany(mappedBy = "shop")
    private List<ShopBill> shopBills;

    @OneToMany(mappedBy = "shop", cascade = CascadeType.ALL)
    private List<ShopProductDto> shopProductDtos;

}
