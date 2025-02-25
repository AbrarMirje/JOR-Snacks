package com.jor.controller;

import com.jor.entity.Location;
import com.jor.exception.LocationNotFoundException;
import com.jor.service.LocationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/location")
@Tag(name = "Location APIs")
@CrossOrigin("*")
public class LocationController {
    private final LocationService locationService;

    @PostMapping("/add")
    public ResponseEntity<?> addLocation(@RequestBody Location location){
        return ResponseEntity.ok(locationService.saveLocation(location));
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<?> getLocation(@PathVariable Long id) throws LocationNotFoundException {
        return ResponseEntity.ok(locationService.getLocation(id));
    }

    @GetMapping("/all")
    public ResponseEntity<?> getLocations(){
        return ResponseEntity.ok(locationService.getLocations());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteLocation(@PathVariable Long id) throws LocationNotFoundException {
        return locationService.deleteLocation(id) ?
                ResponseEntity.ok("Location deleted successfully") :
                ResponseEntity.badRequest().body("Location not deleted");
    }
}
