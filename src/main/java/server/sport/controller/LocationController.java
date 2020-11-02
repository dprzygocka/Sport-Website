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
@RequestMapping("/api/locations")
public class LocationController {
    @Autowired
    LocationRepository locationRepository;

    //////Getting all locations based on start date and end date ??
    @GetMapping("/all")
    public ResponseEntity<List<Location>> getAllLocations (@RequestParam(required = false) String dateTime) throws ParseException { //how to handle exception???
        List<Location> locations;
        if (dateTime.isEmpty()){ //what if the date is not going to be past?
            locations = locationRepository.findAll();
            return new ResponseEntity<>(locations, HttpStatus.OK);
        }
        Date _date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime); //data type is not right, I don;t now why it return: [Wed Nov 15 15:30:14 CET 2017] instead of 2017-11-15 15:30:14.332
        //System.out.println(_date.toString());
        locations = locationRepository.findAllByReservationsDateTime(_date);
        if(locations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
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

    @GetMapping
    public ResponseEntity<Map<String, Object>> getPageOfLocations (
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "3") int size,
            @RequestParam(defaultValue = "courtName,desc") String[] sort) {

        List<Sort.Order> locations = new ArrayList<>();
        if (sort[0].contains(",")) {
            for (String sortOrder : sort) {
                String[] _sort = sortOrder.split(",");
                locations.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            //sort=[field,direction]
            locations.add(new Sort.Order(getSortDirection(sort[1]), sort[0]));
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

    @PostMapping
    public ResponseEntity<Location> createLocation(@RequestBody Location location){
        Location _location = locationRepository.save(location);
        return new ResponseEntity<>(_location, HttpStatus.CREATED);
    }

    @PutMapping("/{location_id}")
    public ResponseEntity<Location> updateLocation(@PathVariable("location_id") int locationId,
                                                   @RequestBody Location location){
        Location _location = locationRepository.findById(locationId).orElseThrow(
                () -> new ResourceNotFoundException("Not found location with id = " + locationId));
        _location.setCourtName(location.getCourtName());
        return new ResponseEntity<>(locationRepository.save(_location),HttpStatus.OK);
    }

    @DeleteMapping("/{location_id}")
    public ResponseEntity<HttpStatus> deleteLocation (@PathVariable("location_id") int locationId){
        locationRepository.deleteById(locationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
