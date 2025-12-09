package com.genieLogiciele.sprint.controller;
import com.genieLogiciele.sprint.entities.Epic;
import com.genieLogiciele.sprint.service.EpicService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/api/epic")
public class EpicController {

    private final EpicService epicService;

    public EpicController(EpicService epicService) {
        this.epicService = epicService;
    }

    @PostMapping("")
    public ResponseEntity<Epic> addEpic(@RequestBody Epic epic)
    {
        return new ResponseEntity<>(epicService.addEpic(epic),HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Epic> getEpic(@PathVariable Long id)
    {
        return new ResponseEntity<>(epicService.getEpic(id),HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
    {
        epicService.deleteEpic(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("")
    public ResponseEntity<Epic> update(@RequestBody Epic epic)
    {
        return new ResponseEntity<>(epicService.updateEpic(epic),HttpStatus.OK);
    }
}
