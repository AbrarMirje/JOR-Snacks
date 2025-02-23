package com.jor.service;

import com.jor.entity.Shop;
import com.jor.entity.ShopBill;
import com.jor.exception.ShopNotFoundException;

import java.util.List;

public interface ShopService {
    Shop addShop(Shop shop);
    Shop getShop(Long id) throws ShopNotFoundException;
    List<Shop> getShops();
    Boolean updateShop(Shop shop, Long id) throws ShopNotFoundException;
    Boolean deleteShop(Long id) throws ShopNotFoundException;

    List<Shop> findShopsByLocation(Long id);

    List<ShopBill> createInvoice(List<ShopBill> shopBills);
}
