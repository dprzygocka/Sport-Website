package server.sport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sport.model.*;
import server.sport.repository.*;

import java.util.Optional;


@RestController
@RequestMapping("/activities")
public class ActivityController {

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    ReservationRepository reservationRepository;

    @Autowired
    LocationRepository locationRepository;

    @Autowired
    ActivityTypeRepository activityTypeRepository;

    @Autowired
    MatchRepository matchRepository;


    @PostMapping("/activity")
    public ResponseEntity<Activity> createActivity (@RequestBody Activity activity){

        Location location = locationRepository.findLocationByCourtName(activity.getReservation().getLocation().getCourtName())
                .orElseThrow(()-> new ResourceNotFoundException("Location does not exist with court name,  " + activity.getReservation().getLocation().getCourtName()));
        activity.getReservation().setLocation(location);

        Reservation reservation = reservationRepository.save(activity.getReservation());
        //Do I need to get userId or are passing userId or userName in JSON????
        //I am assuming that the userId/creator is already passed
        activity.setReservation(reservation);

        ActivityType activityType = activityTypeRepository.findActivityTypeByActivityTypeName(activity.getActivityType().getActivityTypeName())
                    .orElseThrow (()-> new ResourceNotFoundException("Cannot find activity type : " + activity.getActivityType().getActivityTypeName()));

        activity.setActivityType(activityType);

        Activity _activity = activityRepository.save(activity);

        if (activityType.getActivityTypeName().equals("match")){
            activity.getMatch().setActivity(_activity); //back reference issue?
            _activity.setMatch(matchRepository.save(activity.getMatch()));
        }
        return new ResponseEntity<>(_activity, HttpStatus.CREATED);

    }

    @GetMapping("/{activity_id}")
    public ResponseEntity<Optional<Activity>> getActivityById(@PathVariable("activity_id") int activityId){

        Optional<Activity> activity = activityRepository.findById(activityId);
        System.out.println(activity.toString());

        if (activity.equals(null)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(activity, HttpStatus.OK);
    }



}
