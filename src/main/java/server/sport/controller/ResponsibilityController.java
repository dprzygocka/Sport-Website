package server.sport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sport.model.Responsibility;
import server.sport.service.ResponsibilityService;

import java.util.List;

@RestController
@RequestMapping("/api/responsibilities")
public class ResponsibilityController {

    @Autowired
    ResponsibilityService responsibilityService;

    @GetMapping("/sport/{sport_id}")
    public ResponseEntity<List<Responsibility>> getListOfResponsibilitiesBySport(@PathVariable("sport_id") Integer sport_id) {
        return responsibilityService.getListOfResponsibilitiesBySport(sport_id);
    }

    @PutMapping("/{responsibility_id}")
    public ResponseEntity<Responsibility> updateResponsibility(
            @PathVariable("responsibility_id") Integer responsibility_id,
            @RequestBody Responsibility responsibility){

        return  responsibilityService.updateResponsibility(responsibility_id, responsibility);
    }

    @PostMapping
    public ResponseEntity<Responsibility> createResponsibility(
            @RequestBody Responsibility responsibility){
        return responsibilityService.createResponsibility(responsibility);
    }
}
