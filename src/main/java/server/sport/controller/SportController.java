package server.sport.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import server.sport.exception.ResourceNotFoundException;
import server.sport.repository.SportRepository;
import server.sport.model.Sport;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping ("/api/sports")
public class SportController {

    @Autowired
    SportRepository sportRepository;


    //At this stage it is simply making sport name, and is not concerned with the teams that correspond with the sport

    @PostMapping ()
    public ResponseEntity <Sport> createSport (@RequestBody Sport sport) {
        Optional<Sport> _sport = sportRepository.findBySportName(sport.getSportName());
        if(_sport.isEmpty()) {
            Sport newSportEntry = sportRepository.save(sport); //newSportEntry has the new id which was not existing when passing sport
            return new ResponseEntity<>(newSportEntry, HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(_sport.get(), HttpStatus.IM_USED);
        }
    }

    @PutMapping (path="/{sport_id}", consumes="application/json")
    public ResponseEntity <Sport> updateSport (@PathVariable ("sport_id") int sportId, @RequestBody Sport sport){

        Sport updatedSportEntry = sportRepository.findById(sportId).orElseThrow(() -> new ResourceNotFoundException("Not found with id = " + sportId));
        updatedSportEntry.setSportName(sport.getSportName());

        return new ResponseEntity<>(sportRepository.save(updatedSportEntry), HttpStatus.OK) ;
    }

    @GetMapping()
    public ResponseEntity<List<Sport>> getAllSports(){

        List<Sport> sportList = sportRepository.findAll();

        if (sportList.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity<>(sportList, HttpStatus.OK);
        }
    }












}
