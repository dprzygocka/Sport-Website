package server.sport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sport.model.Location;
import server.sport.repository.LocationRepository;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/api")
public class LocationController {

    @Autowired
    LocationRepository locationRepository;


    @GetMapping("/locations")
    public ResponseEntity<List<Location>> getAllLocations (@RequestParam(required = false) String dateTime) throws ParseException { //how to handle exception???
        Date _date = new SimpleDateFormat("dd-MMM-yyyy HH:mm:ss").parse(dateTime); //"31-Dec-1998 23:37:50";  or Thu, Dec 31 1998 23:37:50 but format(E, MMM dd yyyy HH:mm:ss)
        List<Location> locations = locationRepository.findAllByDateAndTime(_date);
        if(locations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    private Sort.Direction getSortDirection(String direction) {
        if (direction.equals("asc")) {
            return Sort.Direction.ASC;
        } else if (direction.equals("desc")) {
            return Sort.Direction.DESC;
        }
        return Sort.Direction.DESC;
    }

    @GetMapping("/locations") //??????????????????????????????????????????????????????????????????
    public ResponseEntity<Map<String, Object>> getPageOfLocations (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "id,desc") String[] sort) {

        List<Location> locations = new ArrayList<>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                locations.add(new Location (getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            //sort=[field,direction]
            locations.add(new Location(getSortDirection(sort[1]), sort[0]));
        }

        Pageable pagingSort = PageRequest.of(page, size, Sort.by(locations));

        Page<Location> pageTuts = locationRepository.findAll(pagingSort);

        List<Location> _locations = pageTuts.getContent();

        if (_locations.isEmpty())
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        Map<String, Object> response = new HashMap<>();
        response.put("tutorials", _locations);
        response.put("currentPage", pageTuts.getNumber());
        response.put("totalItems", pageTuts.getTotalElements());
        response.put("totalPages", pageTuts.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);

    }

    @PostMapping("/locations")
    public ResponseEntity<Location> createLocation(@RequestBody Location location){
        Location _location = locationRepository.save(location);
        return new ResponseEntity<>(_location, HttpStatus.CREATED);
    }

    @PutMapping("/locations/{location_id}")
    public ResponseEntity<Location> updateLocation(@PathVariable("location_id") int locationId){ //why I can't return just HttpStatus???
        Location location = locationRepository.findById(locationId).orElseThrow(
                () -> new ResourceNotFoundException("Not found tutorial with id = " + locationId));
        return new ResponseEntity<>(locationRepository.save(location),HttpStatus.OK);
    }

    @DeleteMapping("/locations/{location_id}")
    public ResponseEntity<HttpStatus> deleteLocation (@PathVariable("location_id") int locationId){
        locationRepository.deleteById(locationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
