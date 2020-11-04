package server.sport.controller;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
public class ReservationControllerTest {

    @Autowired //need to rebase to get this
    ReservationController reservationController;

    @Autowired // need to rebase to get this
    ReservationRepository reservationRepository;







}
