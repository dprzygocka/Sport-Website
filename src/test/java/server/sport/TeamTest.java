package server.sport;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import server.sport.model.Sport;
import server.sport.model.Team;
import server.sport.model.User;
import server.sport.model.UserType;
import server.sport.repository.SportRepository;
import server.sport.repository.TeamRepository;
import server.sport.repository.UserRepository;
import server.sport.repository.UserTypeRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertSame;

@DataJpaTest
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class TeamTest {

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    SportRepository sportRepository;

    @Autowired
    UserTypeRepository userTypeRepository;

    @BeforeEach
    public void init(){teamRepository.deleteAll();}

    @Test
    public void findAllEmptyDatabase() {
        Iterable<Team> teams = teamRepository.findAll();
        assertThat(teams).isEmpty();
    }

    @Test
    public void getTeamsWithSport(){
        Sport sport = sportRepository.save(new Sport("Basketball"));
        teamRepository.save(new Team("Best Team", sport,null));
        teamRepository.save(new Team("Awesomers", sport,null));
        teamRepository.save(new Team("Beasts", sport,null));
        teamRepository.save(new Team("Baddest", sport,null));
        teamRepository.save(new Team("Newbies", sport,null));
        assertSame(5, teamRepository.findAll().size());
    }

    @Test
    public void findById(){
        Sport sport = sportRepository.save(new Sport("Basketball"));
        teamRepository.save(new Team("Best Team", sport,null));
        Team team = teamRepository.save(new Team("Awesomers", sport,null));
        assertSame(team,teamRepository.findById(team.getTeamId()).get());
    }

    @Test
    public void addTeam(){
        assertSame(0, teamRepository.findAll().size());
        Sport sport = sportRepository.save(new Sport("Basketball"));
        teamRepository.save(new Team("Newbies", sport,null));
        assertSame(1, teamRepository.findAll().size());
    }

    @Test
    public void removeUserFromTeam(){
        Sport sport = sportRepository.save(new Sport("Basketball"));
        Team team = teamRepository.save(new Team("Best Team", sport,null));
        UserType userType = userTypeRepository.save(new UserType("player", null));
        userRepository.save(new User("Jason", "Smith", "js@gmail.com", 25, "male", "458945318", null, null, null, null, userType, team));
        User user = userRepository.save(new User("Andrea", "Jones", "js@gmail.com", 15, "female", "458648318", null, null, null, null, userType, team));
        userRepository.save(new User("Henry", "Jackson", "js@gmail.com", 38, "male", "458646818", null, null, null, null, userType, team));
        userRepository.removeFromTeam(user.getUserId());
        // also doesnt work... it assigns team to user...
        assertSame(null, userRepository.findById(user.getUserId()).get().getTeam());

    }

    @Test
    public void updateTeamById(){
        Sport sport = sportRepository.save(new Sport("Basketball"));
        Team t1 = teamRepository.save(new Team("Best Team", sport,null));
        t1.setTeamName("Almost The Best");
        assertSame(t1, teamRepository.save(t1));
    }


}
