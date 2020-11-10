package server.sport.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sport.model.Activity;
import server.sport.repository.ActivityRepository;
import server.sport.repository.UserRepository;
import server.sport.utils.SortCheck;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/profiles/{profile_id}")
public class UserWithIdController {
    @Autowired
    ActivityRepository activityRepository;

    @Autowired
    UserRepository userRepository;

    private boolean sortCriteriaCheck(String sort){
        return sort.equals("startDate") || sort.equals("endDate") || sort.equals("activityId");
    }




    @GetMapping("/activities")
    ResponseEntity<Map<String, Object>> getProfilesActivities(
            @PathVariable("profile_id") int profile_id,
            @RequestParam(defaultValue = "activityId,asc") String sort[],
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size){

        List<Sort.Order> orders = new ArrayList<>();

        if(sort[0].contains(",")){
            for(String sortOrder : sort){
                String[] _sort = sortOrder.split(",");
                if(!sortCriteriaCheck(_sort[0])) throw new IllegalArgumentException("Can't sort according to: " + _sort[0]);
                orders.add(new Sort.Order(SortCheck.getSortDirection(_sort[1]), _sort[0]));
            }
        }else{
            if(!sortCriteriaCheck(sort[0])) throw new IllegalArgumentException("Can't sort according to: " + sort[0]);
            orders.add(new Sort.Order(SortCheck.getSortDirection(sort[1]), sort[0]));
        }

        Pageable pagingSort = PageRequest.of(page, size, Sort.by(orders));
        int team_id = userRepository.findById(profile_id).get().getTeam().getTeamId();
        Page<Activity> activityPage = activityRepository.findAllByTeamTeamId(team_id, pagingSort);

        if(activityPage.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        Map<String, Object> response = new HashMap<>();
        response.put("activities", activityPage.getContent());
        response.put("currentPage", activityPage.getNumber());
        response.put("totalItems", activityPage.getTotalElements());
        response.put("totalPages", activityPage.getTotalPages());

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
