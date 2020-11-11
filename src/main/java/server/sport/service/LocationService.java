package server.sport.service;

import server.sport.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import server.sport.model.Location;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LocationService {
    @Autowired
    LocationRepository locationRepository;

    public ResponseEntity<List<Location>> getAllLocations (String dateTime) throws ParseException { //how to handle exception???
        List<Location> locations;
        if (dateTime.isEmpty()) { //what if the date is not going to be past?
            locations = locationRepository.findAll();
            return new ResponseEntity<>(locations, HttpStatus.OK);
        }
        Date _date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(dateTime); //2017-11-15 15:30:14.332

        //TODO - make sure this method works with new start_at and end_at attributes
        System.out.println(_date.toString());
        locations = locationRepository.findAllByReservationsStartAt(_date);

        //System.out.println(_date.toString());

        if (locations.isEmpty()) {
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

    public ResponseEntity<Map<String, Object>> getPageOfLocations (int page, int size, String[] sort) {
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

    public ResponseEntity<Location> createLocation(Location location) {
        Location _location = locationRepository.save(location);
        return new ResponseEntity<>(_location, HttpStatus.CREATED);
    }

    public ResponseEntity<Location> updateLocation(int locationId, Location location){
        Location _location = locationRepository.findById(locationId).orElseThrow(
                () -> new ResourceNotFoundException("Not found location with id = " + locationId));
        _location.setCourtName(location.getCourtName());
        return new ResponseEntity<>(locationRepository.save(_location),HttpStatus.OK);
    }

    public ResponseEntity<HttpStatus> deleteLocation (int locationId){
        locationRepository.deleteById(locationId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

}
