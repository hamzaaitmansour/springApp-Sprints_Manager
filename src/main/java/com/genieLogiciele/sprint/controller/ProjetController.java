package com.genieLogiciele.sprint.controller;

import com.genieLogiciele.sprint.entities.Projet;
import com.genieLogiciele.sprint.service.ProjetService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/projet")
public class ProjetController {
    private final ProjetService projetService;

    public ProjetController(ProjetService projetService) {
        this.projetService = projetService;
    }

    @PostMapping("")
    public ResponseEntity<Projet> addProjet(@RequestBody Projet projet)
    {
        return new ResponseEntity<>(projetService.add(projet), HttpStatus.CREATED);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Projet> getProjet(@PathVariable Long id)
    {
        return  new ResponseEntity<>(projetService.getById(id),HttpStatus.OK);
    }
    @GetMapping("")
    public ResponseEntity<List<Projet>> getAll()
    {
        return new ResponseEntity<>(projetService.findAll(),HttpStatus.OK);
    }
    @PutMapping("/{id}")
    public ResponseEntity<Projet> updateProjet(@PathVariable Long id, @RequestBody Projet projet)
    {
        return new ResponseEntity<>(projetService.update(id,projet),HttpStatus.ACCEPTED);
    }

}
