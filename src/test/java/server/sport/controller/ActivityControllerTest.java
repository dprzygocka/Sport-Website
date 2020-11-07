package server.sport.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.RequestParam;
import server.sport.model.*;
import server.sport.repository.ActivityRepository;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
public class ActivityControllerTest {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    ActivityController activityController;
/*
    @Test
    public void testAddingActivity(){

        Activity aMockActivity = new Activity();
        aMockActivity.setActivityName("Extreme Ironing practice");
        aMockActivity.setCapacity(6);
        aMockActivity.setDescription("We will be doing the training underwater");
        aMockActivity.setIsCancelled(false);
        aMockActivity.setCreator();
        aMockActivity.setActivityType();
        aMockActivity.setReservation();
        aMockActivity.setActivityStatus();
        aMockActivity.setMatch();
        aMockActivity.setUserResponsibility();
        aMockActivity.setTeam();

        when(activityRepository.save(any(Activity.class))).thenReturn(aMockActivity);

        //save the activity
        Activity newActivity = activityRepository.save(null);

        //Verifying the save
        assertEquals("Extreme Ironing practice", newActivity.getActivityName());
    }



    @Test
    void getActivities(){ //before testing the sorting, it tests if it can even get all the activities
        List<Activity> activities = activityRepository.findAll(10);
        assertEquals(10, activities.size());
    }*/

}
