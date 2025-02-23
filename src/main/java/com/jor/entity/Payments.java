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
public class Payments {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @ManyToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;
    private String invoiceNumber;
    private Double pendingAmount;
    private Double todaysAmount;
    private Double totalAmount;
    private Double receivedAmount;
//    private Boolean isPendingAmount;
//    private Boolean isTodaysAmount;
}
