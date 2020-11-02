package server.sport.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import server.sport.model.Responsibility;
import server.sport.model.Sport;
import server.sport.repository.ResponsibilityRepository;
import server.sport.repository.SportRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ResponsibilityControllerTest {
    @Autowired
    ResponsibilityRepository responsibilityRepository;
    @Autowired
    SportRepository sportRepository;

    @Test
    public void testCreateResponsibility(){
        Sport sport = new Sport("Sport");
        sportRepository.save(sport);
        Responsibility responsibility = new Responsibility(
                "Make laundry",
                sport
        );

        Responsibility responsibilitySaved = responsibilityRepository.save(responsibility);
        assertThat(responsibility.getResponsibilityName().equals(responsibilitySaved.getResponsibilityName()));
    }
}
