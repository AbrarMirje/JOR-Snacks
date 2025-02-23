package com.jor.service;

import com.jor.entity.Location;
import com.jor.exception.LocationNotFoundException;
import software.amazon.awssdk.services.s3.endpoints.internal.Value;

import java.util.List;

public interface LocationService {
    Location saveLocation(Location location);
    Location getLocation(Long id) throws LocationNotFoundException;
    List<Location> getLocations();
    Boolean deleteLocation(Long id) throws LocationNotFoundException;
    Boolean updateLocation(Location location, Long id) throws LocationNotFoundException;
}
