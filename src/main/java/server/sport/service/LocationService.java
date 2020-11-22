package server.sport.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import server.sport.model.Location;
import server.sport.repository.LocationRepository;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class LocationService {
    @Autowired
    LocationRepository locationRepository;

    public ResponseEntity<List<Location>> getAllLocations (Timestamp startAt, Timestamp endAt) throws ParseException {
        List<Location> locations = new ArrayList<>();
        if (endAt != null && startAt != null && startAt.after(endAt)){
            Timestamp tempTimestamp = startAt;
            startAt = endAt;
            endAt = tempTimestamp;
        }
        if (startAt == null){ //what if the date is not going to be past?
            locations = locationRepository.findAll();
            return new ResponseEntity<>(locations, HttpStatus.OK);
        }
        if (endAt == null) {
            //get everything with no reservation, starting after inputted timestamp or ending before inputted timestamp
            locations = locationRepository.findDistinctByReservationsStartAtAfterOrReservationsEndAtBeforeOrReservationsIsNull(startAt, startAt);
        } else if (! (startAt == null && endAt == null)){
            //get all locations that aren't available (as i couldn't negate the query..)
            List<Location> unavailableLocations = locationRepository.findDistinctByReservationsStartAtBeforeAndReservationsEndAtAfter(endAt, startAt);
            //get all locations
            locations = locationRepository.findAll();
            //remove unavailable locations
            locations.removeAll(unavailableLocations);
        }

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

    public ResponseEntity<Location> createLocation(Location location){
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
