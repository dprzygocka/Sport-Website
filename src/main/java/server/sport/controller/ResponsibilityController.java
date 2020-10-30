package server.sport.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sport.model.Responsibility;
import server.sport.repository.ResponsibilityRepository;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/responsibilities")
public class ResponsibilityController {
    @Autowired
    ResponsibilityRepository responsibilityRepository;

    @GetMapping("/{sport_id}")
    public ResponseEntity<List<Responsibility>> getListOfResponsibilitiesBySport(@PathVariable("sport_id") Integer sport_id) {
        try {
            List<Responsibility> responsibilityList = responsibilityRepository.findAllBySportId(sport_id);

            if (!responsibilityList.isEmpty()) {
                return new ResponseEntity<>(responsibilityList, HttpStatus.OK);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{responsibility_id}")
    public ResponseEntity<Responsibility> updateResponsibility(
            @PathVariable("responsibility_id") Integer responsibility_id,
            @RequestBody Responsibility responsibility){

        Optional<Responsibility> optionalResponsibility = responsibilityRepository.findById(responsibility_id);

        if(optionalResponsibility.isPresent()){
            Responsibility _responsibility = optionalResponsibility.get();
            _responsibility.setResponsibilityName(responsibility.getResponsibilityName());
            responsibilityRepository.save(responsibility);
            return new ResponseEntity<>(_responsibility, HttpStatus.OK);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Responsibility> createResponsibility(
            @RequestBody Responsibility responsibility){
        try{
            System.out.println(responsibility);
            Responsibility _responsibility = responsibilityRepository.save(responsibility);
            return new ResponseEntity<>(_responsibility, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
