package server.sport.controller;

import org.hibernate.procedure.NoSuchParameterException;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import server.sport.exception.ResourceNotFoundException;
import server.sport.model.Sport;
import server.sport.model.Team;
import server.sport.model.User;
import server.sport.model.UserType;
import server.sport.repository.SportRepository;
import server.sport.repository.TeamRepository;
import server.sport.repository.UserRepository;
import server.sport.repository.UserTypeRepository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
@SpringBootTest
public class TeamControllerTest {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamController teamController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SportRepository sportRepository;

    @Autowired
    UserTypeRepository userTypeRepository;


    @BeforeEach
    public void init() {
        Sport sport = sportRepository.save(new Sport(1, "Basketball", null));
        Sport sport1 = sportRepository.save(new Sport(2, "Tennis", null));
        Sport sport2 = sportRepository.save(new Sport(3, "Swimming", null));
        UserType userType = userTypeRepository.save(new UserType(1, "player", null));
        Team team1 = teamRepository.save(new Team(1, "Best Team", sport, null, null));
        Team team2 = teamRepository.save(new Team(2, "Awesomers", sport, null, null));
        teamRepository.save(new Team(3, "Beasts", sport, null, null));
        teamRepository.save(new Team(4, "Baddest", sport, null, null));
        teamRepository.save(new Team(5, "Newbies", sport, null, null));
        teamRepository.save(new Team(6, "Nirvanas", sport1, null, null));
        teamRepository.save(new Team(7, "Pros", sport2, null, null));
        userRepository.save(new User(1, "Jason", "Smith", "js@gmail.com", 25, "male", "458945318", null, null, null, null, userType, team1));
        userRepository.save(new User(2, "Andrea", "Jones", "js@gmail.com", 15, "female", "458648318", null, null, null, null, userType, team1));
        userRepository.save(new User(3, "Hana", "Parks", "hp@gmail.com", 45, "female", "458458318", null, null, null, null, userType, team1));
        userRepository.save(new User(4, "Henry", "Jackson", "js@gmail.com", 38, "male", "458646818", null, null, null, null, userType, team2));
    }

    @AfterEach
    public void reset(){
        userRepository.deleteAll();
        userTypeRepository.deleteAll();
        teamRepository.deleteAll();
        sportRepository.deleteAll();
    }

    @Test
    public void getAllTeamsTestSizeSuccessful(){
        assertEquals(7, teamController.getAllTeams().getBody().size());
    }

    @Test
    public void getAllTeamsTestEmpty(){
        userRepository.deleteAll();
        userTypeRepository.deleteAll();
        teamRepository.deleteAll();
        assertEquals(new ResponseEntity<>(HttpStatus.NO_CONTENT), teamController.getAllTeams());
    }

    //fails when run with other tests... Cannot find Sport with id 1
    //when run by itself it passes
    @Test
    public void getAllTeamsTestContentSuccessful(){
        Sport sport = new Sport(1, "Basketball", null);
        Sport sport1 = new Sport(2, "Tennis", null);
        Sport sport2 = new Sport(3, "Swimming", null);
        List<Team> teams = new ArrayList<>(Arrays.asList(
                new Team(1, "Best Team", sport, null, null),
                new Team(2, "Awesomers", sport, null, null),
                new Team(3, "Beasts", sport, null, null),
                new Team(4, "Baddest", sport, null, null),
                new Team(5, "Newbies", sport, null, null),
                new Team(6, "Nirvanas", sport1, null, null),
                new Team(7, "Pros", sport2, null, null)
        ));
        System.out.println(sport.toString());
        System.out.println(sport1.toString());
        System.out.println(teams.toString());
        teamRepository.saveAll(teams);
        assertThat(teamController.getAllTeams().getBody().equals(teams));
    }

    //fails when run with other tests... Cannot find Sport with id 1
    //when run by itself it passes
    @Test
    public void findByIdTestSuccessful(){
        Sport sport = new Sport(1, "Basketball", null);
        Team team = new Team(2, "Awesomers", sport,null, null);
        assertThat(teamController.getTeam(team.getTeamId()).getBody().equals(team));
    }

    @Test
    public void findByIdTestException(){
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            teamController.getTeam(12);
        });
        assertEquals("Did not find team with id = 12", exception.getMessage());
    }

    @Test
    public void addTeamTestSuccessful(){
        int size = teamRepository.findAll().size();
        Sport sport = sportRepository.findBySportName("Basketball").get();
        teamController.addTeam(new Team("Newbies", sport,null));
        assertSame(size+1, teamRepository.findAll().size());
    }

    @Test
    public void addTeamTestNoSportPassedException(){
        Exception exception = assertThrows(NoSuchParameterException.class, () -> {
            teamController.addTeam(new Team(10, "No Sport Team", null, null, null));
        });
        assertEquals("No sport passed", exception.getMessage());
    }

    @Test
    public void addTeamTestNoSportFoundException(){
        Exception exception = assertThrows(ResourceNotFoundException.class, () -> {
            teamController.addTeam(new Team(10, "Wrong Sport Team", new Sport(15,"Diving",null), null, null));
        });
        assertEquals("Sport not found with id = 15", exception.getMessage());
    }

    @Test
    public void removeUserFromTeamTestSuccessful(){
        Team team = teamController.deleteUserFromTeam(1,2).getBody();
        //i have no idea why it doesnt work cause in normal db it does
        assertNull(userRepository.findById(2).get().getTeam());
    }

    @Test
    public void updateTeamByIdTestSuccessful(){
        Sport sport = new Sport(1, "Basketball", null);
        Team team = new Team(1, "Best Team", sport, null, null);
        team.setTeamName("Almost The Best");
        assertEquals(team, teamController.updateTeam(team.getTeamId(),team).getBody());
    }


}
