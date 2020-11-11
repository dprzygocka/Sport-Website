package server.sport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sport.model.Location;
import server.sport.service.LocationService;

import java.text.ParseException;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/locations")
public class LocationController {
    @Autowired
    LocationService locationService;

    //////Getting all locations based on start date and end date ??
    @GetMapping("/all")
    public ResponseEntity<List<Location>> getAllLocations (@RequestParam(required = false) String dateTime) throws ParseException { //how to handle exception???
        return locationService.getAllLocations(dateTime);
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getPageOfLocations (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "courtName,desc") String[] sort) {
        return locationService.getPageOfLocations(page, size, sort);
    }

    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody Location location){
        return locationService.createLocation(location);
    }

    @PutMapping("/{location_id}")
    public ResponseEntity<Location> updateLocation(@PathVariable("location_id") int locationId,
                                                   @RequestBody Location location){
        return locationService.updateLocation(locationId, location);
    }

    @DeleteMapping("/{location_id}")
    public ResponseEntity<HttpStatus> deleteLocation (@PathVariable("location_id") int locationId){
        return locationService.deleteLocation(locationId);
    }

}
