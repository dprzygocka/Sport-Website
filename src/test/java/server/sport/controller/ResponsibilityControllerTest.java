package server.sport.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import server.sport.model.Location;
import server.sport.model.Responsibility;
import server.sport.model.Sport;
import server.sport.repository.ResponsibilityRepository;
import server.sport.repository.SportRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class ResponsibilityControllerTest {
    @Autowired
    ResponsibilityRepository responsibilityRepository;
    @Autowired
    SportRepository sportRepository;
    @Autowired
    ResponsibilityController reponsibilityController;


    @Test
    public void testCreateResponsibility(){
        List<Sport> sports = new ArrayList<>(Arrays.asList(
                new Sport("Tennis"),
                new Sport("Football"),
                new Sport("Ping Pong")
        ));
        sportRepository.saveAll(sports);

        List <Responsibility> responsibilities = new ArrayList<>(Arrays.asList(
                new Responsibility("Bring balls", sports.get(0)),
                new Responsibility("Bring outfits", sports.get(0)),
                new Responsibility("Bring water", sports.get(2))
        ));
        responsibilityRepository.saveAll(responsibilities);
        Responsibility responsibilityNew = new Responsibility("Test if creates responsibilities", sports.get(0));
        ResponseEntity<Responsibility> responsibility1 = reponsibilityController.createResponsibility(responsibilityNew);
        assertThat(responsibility1.getBody().equals(responsibilityNew));
    }
    @Test
    public void testGetListOfResponsibilitiesBySport(){
        List<Sport> sports = new ArrayList<>(Arrays.asList(
                new Sport("Tennis"),
                new Sport("Football"),
                new Sport("Ping Pong")
        ));
        sportRepository.saveAll(sports);

        List <Responsibility> responsibilities = new ArrayList<>(Arrays.asList(
                new Responsibility("Bring balls", sports.get(0)),
                new Responsibility("Bring outfits", sports.get(0)),
                new Responsibility("Bring water", sports.get(2))
        ));
        List<Responsibility> responsibilitiesWithSelectedSport = new ArrayList<>();
        responsibilities.stream().filter(responsibility1 -> responsibility1.getSport().equals(sports.get(0))).forEach(responsibilitiesWithSelectedSport::add);

        responsibilityRepository.saveAll(responsibilities);

        ResponseEntity<List<Responsibility>> responseEntityResponsibilities =
                reponsibilityController.getListOfResponsibilitiesBySport(sports.get(0).getSportId());
        assertEquals(responseEntityResponsibilities.getBody().size(),responsibilitiesWithSelectedSport.size());
    }

    @Test
    public void testUpdateResponsibility(){
        List<Sport> sports = new ArrayList<>(Arrays.asList(
                new Sport("Tennis"),
                new Sport("Football"),
                new Sport("Ping Pong")
        ));
        sportRepository.saveAll(sports);

        List <Responsibility> responsibilities = new ArrayList<>(Arrays.asList(
                new Responsibility("Bring balls", sports.get(0)),
                new Responsibility("Bring outfits", sports.get(1)),
                new Responsibility("Bring water", sports.get(2))
        ));
        responsibilityRepository.saveAll(responsibilities);
        Responsibility newResponsibility = new Responsibility("Updated", sports.get(0));
        int responsibility_id = responsibilityRepository.findAll().get(0).getResponsibilityId();

        ResponseEntity<Responsibility> updatedResponsibility = reponsibilityController.updateResponsibility(responsibility_id, newResponsibility);
        assertThat(updatedResponsibility.getBody().getResponsibilityName().equals(newResponsibility.getResponsibilityName()));


    }
}
