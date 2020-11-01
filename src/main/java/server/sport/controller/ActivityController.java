package server.sport.controller;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< HEAD
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
=======
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
>>>>>>> create activity method
import server.sport.model.*;
import server.sport.repository.*;
import server.sport.model.Activity;
import server.sport.model.Responsibility;
import server.sport.model.UserResponsibility;
import server.sport.repository.ActivityRepository;
import java.util.List;
import java.util.Optional;


<<<<<<< HEAD
=======
@RequestMapping("/activities")
>>>>>>> create activity method
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

<<<<<<< HEAD

    @PostMapping("/activity")
=======
    private Sort.Direction getSortDirection (String direction){

        if (direction.equals("asc")){
            return Sort.Direction.ASC;
        }
        return Sort.Direction.DESC;
    }

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> getPageOfActivities(
            @RequestParam(defaultValue = "0")int page, //activities to be loaded on page 0
            @RequestParam(defaultValue = "3")int size, //3 activities will be fetched from the database
            @RequestParam(defaultValue = "id,desc")String[] sort //ordered by descending order
    ){

        List<Order> orders = new ArrayList<>();
        if (sort[0].contains(",")){
            for (String sortOrder : sort){
                String[] _sort = sortOrder.split(",");
                orders.add(new Order(getSortDirection(_sort[1]), _sort[0]));
            }

        }else{
            orders.add(new Order(getSortDirection(sort[1]), sort[0]));
        }

        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders)); //create Pageable object with page, size, and sort parameters

        //establishes what is on the pageOfActivities.
        Page<Activity> pageOfActivities;
        pageOfActivities = activityRepository.findAll(pagingSort); //includes information relating to the page itself such as size, page number
        List<Activity> activities = pageOfActivities.getContent(); //retrieves List of items in the page

        if (activities.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("activities", activities);
        response.put("currentPage", pageOfActivities.getNumber());
        response.put("totalActivities", pageOfActivities.getTotalElements());
        response.put("totalPages", pageOfActivities.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
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


    @PostMapping("/")
>>>>>>> create activity method
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

<<<<<<< HEAD
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
=======



>>>>>>> create activity method

}
