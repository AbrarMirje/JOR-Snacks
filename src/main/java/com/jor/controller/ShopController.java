package com.jor.controller;

import com.jor.entity.Shop;
import com.jor.entity.ShopBill;
import com.jor.exception.ShopNotFoundException;
import com.jor.response.CustomResponse;
import com.jor.service.ShopService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/shop")
@CrossOrigin("*")
@Tag(name = "Shp APIs")
public class ShopController {
    private final ShopService shopService;

    @PostMapping("/add")
    public ResponseEntity<?> saveShop(@RequestBody Shop shop){
        return ResponseEntity.ok(shopService.addShop(shop));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getShops(){
        return ResponseEntity.ok(shopService.getShops());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getShop(@PathVariable Long id) throws ShopNotFoundException, ShopNotFoundException {
        return ResponseEntity.ok(shopService.getShop(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateShop(@RequestBody Shop shop, @PathVariable Long id) throws ShopNotFoundException, ShopNotFoundException {
        return shopService.updateShop(shop, id) ?
                ResponseEntity.ok(new CustomResponse(true, "Shop updated successfully")) :
                ResponseEntity.badRequest().body(new CustomResponse(false, "Shop not updated"));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteShop(@PathVariable Long id) throws ShopNotFoundException, ShopNotFoundException {
        return shopService.deleteShop(id) ?
                ResponseEntity.ok(new CustomResponse(true, "Shop deleted successfully")) :
                ResponseEntity.badRequest().body(new CustomResponse(false, "Shop not deleted"));
    }

    @GetMapping("/by-location/{id}")
    public ResponseEntity<?> findByLocationId(@PathVariable Long id){
        return ResponseEntity.ok(shopService.findShopsByLocation(id));
    }

    @PostMapping("/create-invoice")
    public ResponseEntity<?> createInvoice(@RequestBody List<ShopBill> shopBills){
        return ResponseEntity.ok(shopService.createInvoice(shopBills));
    }
}
