package com.jor.service.impl;

import com.jor.entity.Location;
import com.jor.exception.LocationNotFoundException;
import com.jor.repository.LocationRepository;
import com.jor.service.LocationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LocationServiceImpl implements LocationService {

    private final LocationRepository locationRepository;

    @Override
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Location getLocation(Long id) throws LocationNotFoundException {
        return locationRepository.findById(id).orElseThrow(
                () -> new LocationNotFoundException("Location not found with id " + id)
        );
    }

    @Override
    public List<Location> getLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Boolean deleteLocation(Long id) throws LocationNotFoundException {
        locationRepository.findById(id).orElseThrow(
                () -> new LocationNotFoundException("Location not found with id " + id)
        );
        locationRepository.deleteById(id);
        return true;
    }

    @Override
    public Boolean updateLocation(Location location, Long id) throws LocationNotFoundException {
        Location oldLocation = locationRepository.findById(id).orElseThrow(
                () -> new LocationNotFoundException("Location not found with id " + id)
        );

        if (location.getLocationName() != null) oldLocation.setLocationName(location.getLocationName());
        locationRepository.save(oldLocation);
        return true;
    }
}
