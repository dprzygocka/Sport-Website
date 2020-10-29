package server.sport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sport.exception.ResourceNotFoundException;
import server.sport.repository.SportRepository;
import server.sport.model.Sport;

import java.util.List;

@RestController
@RequestMapping ("/sport")
public class SportController {

    @Autowired
    SportRepository sportRepository;


    //At this stage it is simply making sport name, and is not concerned with the teams that correspond with the sport
    @PostMapping ("/sport")
    public ResponseEntity <Sport> createSport (@RequestBody Sport sport) {
        Sport newSportEntry = sportRepository.save(sport); //newSportEntry has the new id which was not existing when passing sport
        return new ResponseEntity<>(newSportEntry, HttpStatus.CREATED);
    }

    @PutMapping (path="/{sport_id}", consumes="application/json")
    public ResponseEntity <Sport> updateSport (@PathVariable ("sport_id") int sportId, @RequestBody Sport sport){

        Sport updatedSportEntry = sportRepository.findById(sportId).orElseThrow(() -> new ResourceNotFoundException("Not found with id = " + sportId));
        updatedSportEntry.setSportName(sport.getSportName());

        return new ResponseEntity<>(sportRepository.save(updatedSportEntry), HttpStatus.OK) ;
    }

    @GetMapping("/sport")
    public ResponseEntity<List<Sport>> getAllSports (@RequestParam(required=false) String sportName){

        List<Sport> sportList;

        if (sportName == null){
            sportList = sportRepository.findAll();
        }else{
            sportList = sportRepository.findBySportName(sportName);
        }

        if (sportList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(sportList, HttpStatus.OK);
    }












}
