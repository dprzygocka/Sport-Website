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
import server.sport.exception.EntityCannotBeProcessedExecption;
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
    ResponsibilityRepository responsibilityRepository;

    @Autowired
    UserResponsibilityRepository userResponsibilityRepository;

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
    public ResponseEntity<Integer> updateStatusInActivity (@PathVariable("user_id") int userId, @PathVariable("activity_id") int activityId,
                                                    @RequestParam(required = false) String status){
        Activity activity = activityRepository.findById(activityId).orElseThrow(
                () -> new org.springframework.data.rest.webmvc.ResourceNotFoundException("Not found activity with activity id = " + activityId));

        UserStatus userStatus = userStatusRepository.findByStatusName(status);
        if (userStatus.equals(null))
            new org.springframework.data.rest.webmvc.ResourceNotFoundException("Not found status with user status name = " + status);

        User user = userRepository.findById(userId).orElseThrow(
                () -> new org.springframework.data.rest.webmvc.ResourceNotFoundException("Not found user with user id = " + userId));

        /*List <ActivityStatus> activityStatuses1 = activity.getActivityStatuses().stream().filter
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

         */

        return new ResponseEntity<>(activityStatusRepository.saveNewActivityStatus(userStatus.getStatusId(),activityId, userId), HttpStatus.OK);
    }


    @GetMapping("/{user_id}/activity/{activity_id}")//activity information
    public ResponseEntity<Activity> getActivity(@PathVariable("user_id") int userId, @PathVariable("activity_id") int activityId) {
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("Did not find activity with id = " + activityId));
        return new ResponseEntity<>(activity, HttpStatus.OK);
    }

    @PutMapping("/{user_id}/activity/{activity_id}")
    public ResponseEntity<UserResponsibility> assignUserResponsibility(
            @PathVariable("user_id") Integer userId,
            @PathVariable("activity_id") int activityId,
            @RequestParam int responsibility){
        Activity activity = activityRepository.findById(activityId)
                .orElseThrow(() -> new ResourceNotFoundException("Did not find activity with id = " + activityId));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Did not find user with id = " + userId));
        if (!activity.getTeam().getUsers().contains(user)){
            throw new EntityCannotBeProcessedExecption("Cannot assign responsibility to user from another team. Activity id = " + activityId + " User id = " + userId);
        }
        responsibilityRepository.findById(responsibility)
                .orElseThrow(() -> new ResourceNotFoundException("Did not find responsibility with id = " + responsibility));
        UserResponsibility userResponsibility = userResponsibilityRepository.findById(new UserResponsibilityPK(responsibility,activityId))
                .orElseThrow(() -> new ResourceNotFoundException("Did not find activity with id = " + activityId + " and assigned responsibility with id " + responsibility));
        userResponsibility.setUser(user);
        return new ResponseEntity<>(userResponsibilityRepository.save(userResponsibility), HttpStatus.OK);
    }

    @GetMapping("/user/{user_id}")//
    public ResponseEntity<User> getUser(@PathVariable("user_id") int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Did not find User with id = " + userId));
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    //There is already a delete user from Team method in the TeamController class
    //This method will not deal with team nor activities. The rationale is that the user should still have their historic records within activities
    @DeleteMapping("/user/{user_id}")
    public ResponseEntity<HttpStatus> deleteUser (@PathVariable("user_id") int userId){
        userRepository.deleteByUserId(userId).
               orElseThrow(() -> new ResourceNotFoundException("Did not find User with id = " + userId));
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/update/{user_id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("user_id") int userId) {
        User _user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("Did not find user with id = " + userId));
        _user.setUserId(userId);
        _user.setFirstName(user.getFirstName());
        _user.setLastName(user.getLastName());
        _user.setEmail(user.getEmail());
        _user.setGender(user.getGender());
        _user.setPhone(user.getPhone());
        _user.setEmail(user.getEmail());

        UserType userType= userTypeRepository.findById(user.getUserType().getUserTypeId())
                .orElseThrow(() -> new ResourceNotFoundException("Did not find user type with id = " + user.getUserType().getUserTypeId()));
        _user.setUserType(userType);

        Team team = teamRepository.findById(user.getTeam().getTeamId())
                .orElseThrow(() -> new ResourceNotFoundException("Did not find team with id = " + user.getTeam().getTeamId()));
        _user.setTeam(team);

        _user.setTeam(user.getTeam());

        return new ResponseEntity<>(userRepository.save(_user), HttpStatus.OK);
    }

}

