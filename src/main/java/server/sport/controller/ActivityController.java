package server.sport.controller;

import org.springframework.beans.factory.annotation.Autowired;
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
import server.sport.model.*;
import server.sport.repository.*;


@RequestMapping("/api/activities")
@RestController
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

    @Autowired
    UserRepository userRepository;

    @Autowired
    ResponsibilityRepository responsibilityRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    UserResponsibilityRepository userResponsibilityRepository;

    private Sort.Direction getSortDirection (String direction){

        if (direction.equals("asc")){
            return Sort.Direction.ASC;
        }
        return Sort.Direction.DESC;
    }

    @GetMapping
    public ResponseEntity<Map<String, Object>> getPageOfActivities(
            @RequestParam(defaultValue = "0")int page, //activities to be loaded on page 0
            @RequestParam(defaultValue = "3")int size, //3 activities will be fetched from the database
            @RequestParam(defaultValue = "activityId,desc")String[] sort //ordered by descending order
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

        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));

        //establishes what is on the pageOfActivities.
        Page<Activity> pageOfActivities;
        pageOfActivities = activityRepository.findAll(pagingSort);
        List<Activity> activities = pageOfActivities.getContent();

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
    public ResponseEntity<Activity> getActivityById(@PathVariable("activity_id") int activityId){

        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new server.sport.exception.ResourceNotFoundException("Not found with id = " + activityId));

        if (activity.equals(null)){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(activity, HttpStatus.OK);
    }

    @GetMapping("/teamActivities/{team_id}")
    public ResponseEntity<Collection<Activity>> getActivitiesForTeam(@PathVariable("team_id") int teamId){
        Team team = teamRepository.findById(teamId).orElseThrow(
                () -> new server.sport.exception.ResourceNotFoundException("Not found with id = " + teamId));
        Collection <Activity> activities = team.getActivities();

        if (activities.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(activities, HttpStatus.OK);
    }


    @PostMapping("/{activity_id}") //NOT WORKING WHYYYYYYYYYYYY
    public ResponseEntity<Activity> updateActivityInformation (@PathVariable("activity_id") int activityId, @RequestBody(required = false) Responsibility responsibility){
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new server.sport.exception.ResourceNotFoundException("Not found with id = " + activityId));

        Responsibility responsibility1 = responsibilityRepository.findById(responsibility.getResponsibilityId()).orElseThrow(
                () -> new server.sport.exception.ResourceNotFoundException("Not found with id = " + responsibility.getResponsibilityId()));

        UserResponsibility userResponsibility = new UserResponsibility(activityId, responsibility1, null, activity);
        //should I save new User resposibility ti db? or it it saved along with the list of userResposibilities?????????????????????????????????????????????
        UserResponsibility userResponsibility1 = userResponsibilityRepository.save(userResponsibility);
        activity.getUserResponsibilities().add(userResponsibility1);
        activityRepository.save(activity);
        return (new ResponseEntity<>(activity, HttpStatus.CREATED));
    }


    @PostMapping
    public ResponseEntity<Activity> createActivity (@RequestBody Activity activity){

        Location location = locationRepository.findById(activity.getReservation().getLocation().getLocationId())
                .orElseThrow(()-> new ResourceNotFoundException("Location does not exist with court name,  " + activity.getReservation().getLocation().getLocationId()));
        activity.getReservation().setLocation(location);

        Reservation res = activity.getReservation();
        Reservation reservation = reservationRepository.save(res);
        //Do I need to get userId or are passing userId or userName in JSON????
        //I am assuming that the userId/creator is already passed
        activity.setReservation(reservation);

        String activityTypeName = activity.getActivityType().getActivityTypeName();
        ActivityType activityType = activityTypeRepository.findActivityTypeByActivityTypeName(activityTypeName)
                    .orElseThrow (()-> new ResourceNotFoundException("Cannot find activity type : " + activityTypeName));

        activity.setActivityType(activityType);

        User creator = userRepository.findById(activity.getCreator().getUserId()).get();
        activity.setCreator(creator);
        Activity _activity = activityRepository.save(activity);

        if (activityType.getActivityTypeName().equals("match")){
            Match match = new Match();
            match.setActivity(_activity);
            matchRepository.save(match);
            //activity.getMatch().setActivity(_activity); //back reference issue?
            //_activity.setMatch(matchRepository.save(activity.getMatch()));
        }
        return new ResponseEntity<>(_activity, HttpStatus.CREATED);

    }





}
