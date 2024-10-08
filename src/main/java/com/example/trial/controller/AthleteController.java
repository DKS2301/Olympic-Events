package com.example.trial.controller;

import com.example.trial.Exceptions.ResourceNotFoundException;
import com.example.trial.model.Athlete;
import com.example.trial.model.Events;
import com.example.trial.services.DataGenerationService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("events/{name}/athletes")
public class AthleteController {
    @Autowired
    private DataGenerationService dataGenerationService;
    @Transactional

    @GetMapping
    public List<Athlete> getAllAthletes(@PathVariable String name) {
        Events event = dataGenerationService.getEventByName(name);
        if(event ==null)
            throw new ResourceNotFoundException("No events found for name " + name);
        if (dataGenerationService.findAthletesByEvent(event).isEmpty())
            throw new ResourceNotFoundException("No Athletes found");
        return dataGenerationService.findAthletesByEvent(event);
    }

    @Transactional
    @GetMapping("/{id}")
    public Athlete getAthleteById(@PathVariable Long id,@PathVariable String name) {
        Events event = dataGenerationService.getEventByName(name);
        if(event ==null)
            throw new ResourceNotFoundException("No events found for name " + name);
        if (dataGenerationService.getAthleteByEventId(event,id)==null)
            throw new ResourceNotFoundException("No Athlete found with id " + id);
        return dataGenerationService.getAthleteById(id);
    }

    @Transactional
    @PostMapping
    public Athlete createAthlete(@RequestBody Athlete athlete) {
        return dataGenerationService.saveAthlete(athlete);
    }
    @Transactional
    @PutMapping("/{id}")
    public Athlete updateAthlete(@RequestBody Athlete athlete, @PathVariable Long id) {
        if (dataGenerationService.getAthleteById(id)==null)
            throw new ResourceNotFoundException("No Athlete found with id " + id);
        return dataGenerationService.saveAthlete(athlete);
    }

    @Transactional
    @DeleteMapping("/{id}")
    public void deleteAthlete(@PathVariable long id) {
        if (dataGenerationService.getAthleteById(id)==null)
            throw new ResourceNotFoundException("No Athlete found with id " + id);
        dataGenerationService.deleteAthlete(id);
    }
}


