package server.sport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sport.model.Activity;
import server.sport.model.User;
import server.sport.model.UserResponsibility;
import server.sport.service.UserService;

import java.util.Map;

@RestController
@RequestMapping("/api/profiles")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping
    public ResponseEntity<User> addUser(@RequestBody User user) {
        return userService.addUser(user);
    }

    @GetMapping("/{team_id}") //profiles in the club (basic info - team, name, surname, email)
    public ResponseEntity<Map<String, Object>> getTeamMembers(
        @PathVariable("team_id") int teamId,
        @RequestParam(defaultValue = "userId,desc") String[] sort,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size){
        return userService.getTeamMembers(teamId, sort, page, size);
    }

    //Method updates activity with user status
    @PutMapping("/{user_id}/{activity_id}")
    public ResponseEntity<Integer> updateStatusInActivity (@PathVariable("user_id") int userId, @PathVariable("activity_id") int activityId,
                                                    @RequestParam(required = false) String status){
        return userService.updateStatusInActivity(userId, activityId, status);
    }

    @GetMapping("/{user_id}/activity/{activity_id}")//activity information
    public ResponseEntity<Activity> getActivity(@PathVariable("user_id") int userId, @PathVariable("activity_id") int activityId) {
        return userService.getActivity(userId, activityId);
    }

    @PutMapping("/{user_id}/activity/{activity_id}")
    public ResponseEntity<UserResponsibility> assignUserResponsibility(
            @PathVariable("user_id") Integer userId,
            @PathVariable("activity_id") int activityId,
            @RequestParam int responsibility){
        return userService.assignUserResponsibility(userId, activityId, responsibility);
    }

    @GetMapping("/user/{user_id}")//
    public ResponseEntity<User> getUser(@PathVariable("user_id") int userId) {
        return userService.getUser(userId);
    }

    //There is already a delete user from Team method in the TeamController class
    //This method will not deal with team nor activities. The rationale is that the user should still have their historic records within activities
    @DeleteMapping("/user/{user_id}")
    public ResponseEntity<HttpStatus> deleteUser (@PathVariable("user_id") int userId){
        return userService.deleteUser(userId);
    }

    @PutMapping("/update/{user_id}")
    public ResponseEntity<User> updateUser(@RequestBody User user, @PathVariable("user_id") int userId) {
        return userService.updateUser(user, userId);
    }

}

