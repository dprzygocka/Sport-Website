package server.sport.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sport.controller.UserController;
import server.sport.exception.EntityCannotBeProcessedExecption;
import server.sport.exception.ResourceNotFoundException;
import server.sport.model.*;
import server.sport.repository.*;

import java.sql.Timestamp;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

@SpringBootTest
public class UserControllerTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    SportRepository sportRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    UserTypeRepository userTypeRepository;

    @Autowired
    ActivityStatusRepository activityStatusRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    ResponsibilityRepository responsibilityRepository;

    @Autowired
    ActivityTypeRepository activityTypeRepository;

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    UserStatusRepository userStatusRepository;

    @Autowired
    UserResponsibilityRepository userResponsibilityRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    UserController userController;

    //TESTS DON'T RUN TOGETHER BUT WORK ONE BY ONE
    @BeforeEach
    public void init() {
        Sport sport = sportRepository.save(new Sport(1, "Basketball", null));
        Sport sport1 = sportRepository.save(new Sport(2, "Tennis", null));
        UserType userType = userTypeRepository.save(new UserType(1, "player", null));
        UserType userType2 = userTypeRepository.save(new UserType(2, "coach", null));
        Team team1 = teamRepository.save(new Team(1, "Best Team", sport, null, null));
        Team team2 = teamRepository.save(new Team(2, "Awesomers", sport1, null, null));
        User user1 = userRepository.save(new User(1, "Jason", "Smith", "js@gmail.com", 25, "male", "458945318", null, null, null, null, userType, team1));
        userRepository.save(new User(2, "Andrea", "Jones", "js@gmail.com", 15, "female", "458648318", null, null, null, null, userType, team1));
        userRepository.save(new User(3, "Hana", "Parks", "hp@gmail.com", 45, "female", "458458318", null, null, null, null, userType2, team1));
        User user2 = userRepository.save(new User(4, "Henry", "Jackson", "js@gmail.com", 38, "male", "458646818", null, null, null, null, userType, team2));
        ActivityType matchType = activityTypeRepository.save(new ActivityType("match", null));
        ActivityType trainingType = activityTypeRepository.save(new ActivityType("training", null));
        Location location = locationRepository.save(new Location(1, "Court in Lyngby", null));
        Reservation reservation = reservationRepository.save(new Reservation(1, new Timestamp(new GregorianCalendar(2020, 3, 24).getTimeInMillis()), new Timestamp(new GregorianCalendar(2020, 3, 24).getTimeInMillis() + 36000), null, location));
        Reservation reservation2 = reservationRepository.save(new Reservation(2, new Timestamp(new GregorianCalendar(2020, 3, 25).getTimeInMillis()), new Timestamp(new GregorianCalendar(2020, 3, 25).getTimeInMillis() + 36000), null, location));
        Activity activity1 = activityRepository.save(new Activity(1,"Our first match", 20, "Let's win this", false, user1, matchType, reservation, null, null, null, team1));
        Activity activity2 = activityRepository.save(new Activity(2,"Hard training", 20, "We will sweat a lot", false, user2, trainingType, reservation2, null, null, null, team2));
        Responsibility responsibility = responsibilityRepository.save(new Responsibility(1, "Wash clothes", sport, null));
        //cannot insert...
        //UserResponsibility userResponsibility = userResponsibilityRepository.save(new UserResponsibility(responsibility, null, activity1));
        Match match = matchRepository.save(new Match(1, 5, null,activity1));
        UserStatus userStatus = userStatusRepository.save(new UserStatus(1, "Attending", null));
        UserStatus userStatus2 = userStatusRepository.save(new UserStatus(2, "Not Attending", null));
        //cannot insert...
        //activityStatusRepository.save(new ActivityStatus(2,1, 1));
    }

    @AfterEach
    public void reset(){
        matchRepository.deleteAll();
        responsibilityRepository.deleteAll();
        userResponsibilityRepository.deleteAll();
        userStatusRepository.deleteAll();
        activityStatusRepository.deleteAll();
        activityRepository.deleteAll();
        reservationRepository.deleteAll();
        locationRepository.deleteAll();
        activityTypeRepository.deleteAll();
        userRepository.deleteAll();
        userTypeRepository.deleteAll();
        teamRepository.deleteAll();
        sportRepository.deleteAll();
    }

    @Test
    public void addNewUserTestSuccessful(){
        int size = userRepository.findAll().size();
        Sport sport = new Sport(1, "Basketball", null);
        Team team =new Team(1, "Best Team", sport, null, null);
        UserType userType = new UserType(1,"player", null);
        userRepository.save(new User(5,"Alexander", "Json", "aj@gmail.com", 24, "male", "458945898", null, null, null, null, userType, team));
        assertSame(size+1, userRepository.findAll().size());
    }


    //cannot test as activity status cannot be inserted
  /*  @Test
    public void updateStatusInActivityTestSuccessful (){
        int updated = userController.updateStatusInActivity(1,1, "Attending").getBody();
        assertSame(1, updated);
    }
*/


    @Test
    public void getUserActivityTestSuccessful (){
        Sport sport = new Sport(1, "Basketball", null);
        Team team1 = new Team(1, "Best Team", sport, null, null);
        UserType userType = new UserType(1, "player", null);
        User user1 = new User(1, "Jason", "Smith", "js@gmail.com", 25, "male", "458945318", null, null, null, null, userType, team1);
        ActivityType matchType = new ActivityType("match", null);
        Location location = new Location(1, "Court in Lyngby", null);
        Reservation reservation = new Reservation(1, new Timestamp(new GregorianCalendar(2020, 3, 24).getTimeInMillis()), new Timestamp(new GregorianCalendar(2020, 3, 24).getTimeInMillis() + 36000), null, location);
        Activity activity = new Activity(1,"Our first match", 20, "Let's win this", false, user1, matchType, reservation, null, null, null, team1);
        assertThat(activity.equals(userController.getActivity(1,1).getBody()));
    }

    //cannot test as user responsibility cannot be inserted
  /*  @PutMapping("/{user_id}/activity/{activity_id}")
    public ResponseEntity<UserResponsibility> assignUserResponsibility(
        return new ResponseEntity<>(userResponsibilityRepository.save(userResponsibility), HttpStatus.OK);
    }
*/
    @Test
    public void getUserTestSuccessful() {
        Sport sport = new Sport(1, "Basketball", null);
        Team team1 = new Team(1, "Best Team", sport, null, null);
        UserType userType = new UserType(1, "player", null);
        User user1 = new User(1, "Jason", "Smith", "js@gmail.com", 25, "male", "458945318", null, null, null, null, userType, team1);
        assertEquals(user1,userController.userService.getUserByEmail(user1.getEmail()).getBody());
    }

    @Test
    public void deleteUserTestSuccessful (){
        User user = userRepository.findById(3).get();
        assertEquals("204 NO_CONTENT",(userController.deleteUser(user.getUserId()).getStatusCode().toString()));
    }

    @Test
    public void updateUserTestSuccessful(){
        User user = userRepository.findById(1).get();
        user.setFirstName("Lars");
        user.setLastName("Johansen");
        assertEquals(user, userController.updateUser(user, 1).getBody());
    }

   /* @Test
    public void getUserPageByTeamId(){
        User user = userRepository.save(new User("Jason", "Smith", "js@gmail.com", 25, "male", "458945318", null, null, null, null, new UserType("player", null), new Team("Awesomers", new Sport("Basketball",null, null), null)));
        userRepository.save(new User("Andrea", "Jones", "js@gmail.com", 15, "female", "458648318", null, null, null, null, new UserType("player", null), new Team("Fnatic", new Sport("Ice Hockey",null, null), null)));
        userRepository.save(new User("Henry", "Jackson", "js@gmail.com", 38, "male", "458646818", null, null, null, null, new UserType("coach", null), new Team("Awesomers", new Sport("Football",null, null), null)));
        Page<User> userPage;
        assertSame( userPage, userRepository.findAllByTeamTeamId(1, PageRequest.of(1, 1, Sort.by(new Sort.Order(Sort.Direction.ASC,"firstName"))));
    }*/

}
