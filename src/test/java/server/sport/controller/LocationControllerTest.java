package server.sport.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import server.sport.controller.LocationController;
import server.sport.model.Location;
import server.sport.repository.LocationRepository;
import server.sport.repository.SportRepository;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class LocationControllerTest {
    @Autowired
    LocationController locationController;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    SportRepository sportRepository;

    //--- Create Location Test ---//

    @Test
    void contextLoads() {
    }

    @Test
    public void createLocationTest(){
        Location location = new Location("Court 2");
        ResponseEntity<Location> responseEntity = locationController.createLocation(location);

        assertThat(responseEntity.getBody().getCourtName().equals(location.getCourtName()));
    }

    //--- Put mapping Location update test ---//

    @Test
    public void updateLocationTest(){
        Iterable<Location> locations = new ArrayList<>(Arrays.asList(
                new Location("Court 1"),
                new Location("Court 2"),
                new Location("Court 3")
        ));
        locationRepository.saveAll(locations);

        int location_id = locationRepository.findAll().get(0).getLocationId();
        String newLocationName = "New location :D ";

        ResponseEntity<Location> locationResponseEntity
         = locationController.updateLocation(location_id, new Location(newLocationName));

        assertThat(locationResponseEntity.getBody().getCourtName().equals(newLocationName));
    }
}
