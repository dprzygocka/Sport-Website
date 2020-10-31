package server.sport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sport.model.Activity;
import server.sport.model.Responsibility;
import server.sport.model.UserResponsibility;
import server.sport.repository.ActivityRepository;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/activity")
public class ActivityController {
    @Autowired
    ActivityRepository activityRepository;

    @GetMapping("/{activity_id}")
    public ResponseEntity<Optional<Activity>> getActivityById(@PathVariable("activity_id") int activityId){

        Optional<Activity> activity = activityRepository.findById(activityId);
        System.out.println(activity.toString());

        if (activity.equals(null)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(activity, HttpStatus.OK);
    }

    @GetMapping("/teamActivities/{team_id}")
    public ResponseEntity<List<Activity>> getActivitiesForTeam(@PathVariable("team_id") int teamId){

        List<Activity> activities = activityRepository.findByTeamTeamId(teamId);

        if (activities.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }


    @PostMapping("/{activity_id}")
    public ResponseEntity<Activity> updateActivityInformation (@PathVariable("activity_id") int activityId, @RequestBody(required = false) Responsibility responsibility){
        Activity activity = activityRepository.findByActivityId(activityId);

        if (activity.equals(null))
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);

        UserResponsibility userResponsibility = new UserResponsibility(activityId, responsibility, null, activity);
        //should I save new User resposibility ti db? or it it saved along with the list of userResposibilities
        activity.getUserResponsibilities().add(userResponsibility);
        activityRepository.save(activity);
        return (new ResponseEntity<>(activity, HttpStatus.CREATED));
    }

}
