package server.sport.controller;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import server.sport.model.Responsibility;
import server.sport.model.Sport;
import server.sport.repository.ResponsibilityRepository;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class ResponsibilityControllerTest {
    @Autowired
    ResponsibilityRepository responsibilityRepository;

    @Test
    public void testCreateResponsibility(){
        Responsibility responsibility = new Responsibility(
                "Make laundry",
                new Sport("Football")
        );

        Responsibility responsibilitySaved = responsibilityRepository.save(responsibility);

        assertThat(responsibility.getResponsibilityName().equals(responsibilitySaved.getResponsibilityName()));

    }
}
