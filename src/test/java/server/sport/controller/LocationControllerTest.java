package server.sport.controller;


import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import server.sport.model.Location;
import server.sport.repository.LocationRepository;
import server.sport.repository.SportRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
//@RunWith(SpringRunner.class)
//@SpringBootTest
public class LocationControllerTest {
    @Autowired
    LocationController locationController;
    @Autowired
    LocationRepository locationRepository;
    @Autowired
    SportRepository sportRepository;


    //--- Create Location Test ---//

    @Test
    public void contextLoads() {
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


    //I'm aware that this doesn't test controller. I just need to test interact with the H2 database first
    @Test
    @Rollback(false)
    public void whenDeletingLocationFromRepoItShouldBeSuccessful(){
        locationRepository.deleteById(6); //does not find id 6
        List<Location> deletedLocation = locationRepository.findByCourtName("Basketball Pitch 2");
        assertThat(deletedLocation).isNull();
    }

    @Test
    @Rollback(false)
    public void testLocationIsDeleted(){
        Integer id = 6;
        boolean existingBeforeDelete = locationRepository.findById(id).isPresent();
        locationRepository.deleteById(id);
        boolean notExist= locationRepository.findById(id).isPresent();
        assertTrue(existingBeforeDelete);
        assertFalse(notExist);
    }


}
