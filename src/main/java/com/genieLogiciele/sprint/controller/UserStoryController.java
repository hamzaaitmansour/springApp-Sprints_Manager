package com.genieLogiciele.sprint.controller;

import com.genieLogiciele.sprint.entities.UserStory;
import com.genieLogiciele.sprint.service.BacklogService;
import com.genieLogiciele.sprint.service.EpicService;
import com.genieLogiciele.sprint.service.UserStorySerice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/userstory")
public class UserStoryController {

    @Autowired
    private EpicService epicService;

    @Autowired
    private UserStorySerice userStorySerice;

    @Autowired
    private BacklogService backlogService;


    @PostMapping("/{id}")
    public void addUserStory(@PathVariable Long id , @RequestBody UserStory userStory)
    {
        userStorySerice.addUserStory(userStory,id);
    }
    @GetMapping("/byEpic/{id}")
    public ResponseEntity<List<UserStory>> findByEpic(@PathVariable Long id)
    {
        return new ResponseEntity<>(userStorySerice.getAllByEpic(id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id)
    {
        userStorySerice.deleteUserStory(id);
        return ResponseEntity.noContent().build();

    }






}
