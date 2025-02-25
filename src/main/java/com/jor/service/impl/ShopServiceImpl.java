package com.jor.service.impl;

import com.jor.entity.Payments;
import com.jor.entity.Product;
import com.jor.entity.Shop;
import com.jor.entity.ShopBill;
import com.jor.entity.dto.ShopProductDto;
import com.jor.exception.ShopNotFoundException;
import com.jor.repository.PaymentRepository;
import com.jor.repository.ProductRepository;
import com.jor.repository.ShopBillRepository;
import com.jor.repository.ShopRepository;
import com.jor.service.ShopService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ShopServiceImpl implements ShopService {
    private final ShopRepository shopRepository;
    private final ShopBillRepository shopBillRepository;
    private final ProductRepository productRepository;
    private final PaymentRepository paymentRepository;

    @Override
    public Shop addShop(Shop shop) {
        if (shop.getShopProductDtos() != null) {
            for (ShopProductDto productDto : shop.getShopProductDtos()) {
                productDto.setShop(shop);
            }
        }
        return shopRepository.save(shop);
    }


    @Override
    public Shop getShop(Long id) throws ShopNotFoundException {
        return shopRepository.findById(id).orElseThrow(
                () -> new ShopNotFoundException("Shop not found with id " + id)
        );
    }

    @Override
    public List<Shop> getShops() {
        return shopRepository.findAll();
    }

    @Override
    public Boolean updateShop(Shop shop, Long id) throws ShopNotFoundException {
        Shop oldShop = shopRepository.findById(id).orElseThrow(
                () -> new ShopNotFoundException("Shop not found with id " + id)
        );

        if (shop.getShopName() != null) oldShop.setShopName(shop.getShopName());
        if (shop.getContact() != null) oldShop.setContact(shop.getContact());
        if (oldShop.getLocation() != null) oldShop.setLocation(shop.getLocation());

        shopRepository.save(oldShop);
        return true;
    }

    @Override
    public Boolean deleteShop(Long id) throws ShopNotFoundException {
        shopRepository.findById(id).orElseThrow(
                () -> new ShopNotFoundException("Shop not found with id " + id)
        );
        shopRepository.deleteById(id);
        return true;
    }

    @Override
    public List<Shop> findShopsByLocation(Long id)
    {
        return shopRepository.findByLocation_LocationId(id);
    }

    @Override
    @Transactional
    public List<ShopBill> createInvoice(List<ShopBill> shopBills) {
        List<ShopBill> shopBillList = new ArrayList<>();

        String invoiceNumber = generateInvoiceNumber();

        for (ShopBill shopBill : shopBills) {
            Product product = productRepository.findById(shopBill.getProductId()).orElseThrow(
                    () -> new RuntimeException("Product not found with ID: " + shopBill.getProductId()));

            if (shopBill.getQuantity() > product.getQuantity()) {
                throw new RuntimeException("Insufficient stock for product ID: " + shopBill.getProductId());
            }

            Shop shop = shopRepository.findById(shopBill.getShop().getShopId()).orElseThrow(
                    () -> new RuntimeException("Shop not found with ID: " + shopBill.getShop().getShopId()));

            System.out.println("we are here");

            shop.getShopProductDtos().forEach(shopProductDto -> {
                shopBill.setRate(shopProductDto.getShopProductPrice());
                shopBill.setProductId(product.getProductId());
                shopBill.setInvoiceNumber(invoiceNumber);
                shopBill.setTotal(shopProductDto.getShopProductPrice() * shopBill.getQuantity());
                shopBillList.add(shopBillRepository.save(shopBill));

            });
            product.setQuantity(product.getQuantity() - shopBill.getQuantity());
            productRepository.save(product);

        }
        System.out.println("We are here 2");
        ShopBill shopBill = shopBillList.stream().findFirst().get();
        Shop shop = shopRepository.findById(shopBill.getShop().getShopId()).get();
        Payments payments = new Payments();
        payments.setShop(shop);
        payments.setInvoiceNumber(shopBill.getInvoiceNumber());
        payments.setTodaysAmount(0.0);
        payments.setReceivedAmount(0.0);
        Double pendingAmount = 0.0;
        for (ShopBill sh : shopBillList){
            pendingAmount += sh.getTotal();
        }
        payments.setPendingAmount(pendingAmount);
        payments.setTotalAmount(0.0);
        paymentRepository.save(payments);
        return shopBillList;
    }


    private String generateInvoiceNumber() {
        String datePart = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));

        Optional<ShopBill> lastBill = shopBillRepository.findTopByInvoiceNumberStartingWithOrderByInvoiceNumberDesc("INV-" + datePart);

        if (lastBill.isPresent()) {
            String lastInvoice = lastBill.get().getInvoiceNumber();
            int lastSequence = Integer.parseInt(lastInvoice.substring(lastInvoice.lastIndexOf("-") + 1));
            return "INV-" + datePart + "-" + String.format("%04d", lastSequence + 1);
        } else {
            return "INV-" + datePart + "-0001";
        }
    }
}
