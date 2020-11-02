package server.sport.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import server.sport.model.Location;
import server.sport.repository.LocationRepository;
import server.sport.repository.SportRepository;

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
        System.out.println(locationRepository.findAll());
        System.out.println(sportRepository.findAll());
        int location_id = locationRepository.findAll().get(0).getLocationId();
        String newLocationName = "New location :D ";

        ResponseEntity<Location> locationResponseEntity
         = locationController.updateLocation(location_id, new Location(newLocationName));

        assertThat(locationResponseEntity.getBody().getCourtName().equals(newLocationName));
    }
}
