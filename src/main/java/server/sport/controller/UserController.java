package server.sport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sport.exception.ErrorMessage;
import server.sport.exception.ResourceNotFoundException;
import server.sport.model.*;
import server.sport.model.helper.BasicUser;
import server.sport.repository.*;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/profiles")
public class UserController {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserTypeRepository userTypeRepository;

    @Autowired
    TeamRepository teamRepository;

    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    UserStatusRepository userStatusRepository;

    @Autowired
    ActivityStatusRepository activityStatusRepository;

    private Sort.Direction getSortDirection(String direction){
        if(direction.equals("asc")){
            return Sort.Direction.ASC;
        }
        return Sort.Direction.DESC;
    }

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        userTypeRepository.findById(user.getUserType().getUserTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("User type with id " + user.getUserType().getUserTypeId() + " not found."));
        teamRepository.findById(user.getTeam().getTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Team with id " + user.getTeam().getTeamId() + " not found."));
        return new ResponseEntity<>(userRepository.save(user), HttpStatus.OK);
    }

    private boolean sortCriteriaCheck(String sort){
        return sort.equals("firstName") || sort.equals("lastName") || sort.equals("userId");
    }

    @GetMapping("/{team_id}") //profiles in the club (basic info - team, name, surname, email)
    public ResponseEntity<Map<String, Object>> getTeamMembers(
        @PathVariable("team_id") int teamId,
        @RequestParam(defaultValue = "userId,desc") String[] sort,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size){

        List<Sort.Order> orders= new ArrayList<>();
        if(sort[0].contains(",")){
            for(String sortOrders: sort){
                String[] _sort = sortOrders.split(",");
                if (!sortCriteriaCheck(_sort[0])) throw new IllegalArgumentException("Can't sort according to: " + _sort[0]);
                orders.add(new Sort.Order(getSortDirection(_sort[1]), _sort[0]));
            }
        } else {
            if (!sortCriteriaCheck(sort[0])) throw new IllegalArgumentException("Can't sort according to: " + sort[0]);
            orders.add(new Sort.Order(getSortDirection(sort[1]),sort[0]));
        }

        Pageable pagingSort = PageRequest.of(page, size,Sort.by(orders));
        Page<User> pageUsers = userRepository.findAllByTeamTeamId(teamId, pagingSort);
        if (pageUsers.getContent().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        List<BasicUser> basicUsers = new ArrayList<>();
        for (User user: pageUsers.getContent()) {
            Team tempTeam = user.getTeam();
            Sport tempSport = tempTeam.getSport();
            basicUsers.add(new BasicUser(user.getUserId(),user.getFirstName(),user.getLastName(),user.getEmail(),
                new Team(tempTeam.getTeamId(),tempTeam.getTeamName(),
                new Sport(tempSport.getSportId(),tempSport.getSportName(), tempSport.getResponsibilities())))
            );
        }

        Map<String,Object> response = new HashMap<>();
        response.put("users", basicUsers);
        response.put("currentPage", pageUsers.getNumber());
        response.put("totalItems", pageUsers.getTotalElements());
        response.put("totalPages", pageUsers.getTotalPages());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    //Method updates activity with user status
    @PutMapping("/{user_id}/{activity_id}")
    public ResponseEntity<ActivityStatus> updateStatusInActivity (@PathVariable("user_id") int userId, @PathVariable("activity_id") int activityId,
                                                    @RequestParam(required = false) String status){
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new org.springframework.data.rest.webmvc.ResourceNotFoundException("Not found activity with activity id = " + activityId));

        UserStatus userStatus = userStatusRepository.findByStatusName(status);
        if (userStatus.equals(null))
            new org.springframework.data.rest.webmvc.ResourceNotFoundException("Not found status with user status name = " + status);

        User user = userRepository.findById(userId).orElseThrow(
                () -> new org.springframework.data.rest.webmvc.ResourceNotFoundException("Not found user with user id = " + userId));

        List <ActivityStatus> activityStatuses1 = activity.getActivityStatuses().stream().filter
                (activityStatus1 -> activityStatus1.getUserId()==userId).collect(Collectors.toList());

        ActivityStatus activityStatus = null;

        if (activityStatuses1.size() == 1){
            activityStatus = new ActivityStatus(userId, activityId, userStatus, user, activity);
        }else if (activityStatuses1.size() > 1 ){
            new ErrorMessage(403, new Date(), "Too many activityStauses for one users", "Found more than one record of activity status for one user");
        }else {
            activityStatus = activityStatuses1.get(0);
            activityStatus.setUserStatus(userStatus);
        }

        return new ResponseEntity<>(activityStatusRepository.save(activityStatus), HttpStatus.OK);
    }

}

