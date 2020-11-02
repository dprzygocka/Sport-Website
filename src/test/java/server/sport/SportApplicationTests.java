package server.sport;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import server.sport.repository.LocationRepository;

@SpringBootTest
class SportApplicationTests {
    @Autowired
    LocationRepository locationRepository;
    @Test
    void contextLoads() {
        System.out.println(locationRepository.findById(1));
    }

}
