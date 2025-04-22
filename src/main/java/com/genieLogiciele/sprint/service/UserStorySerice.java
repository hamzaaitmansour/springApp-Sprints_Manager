package com.genieLogiciele.sprint.service;

import com.genieLogiciele.sprint.entities.Epic;
import com.genieLogiciele.sprint.entities.UserStory;
import com.genieLogiciele.sprint.exception.EntityNotFound;
import com.genieLogiciele.sprint.repo.EpicRepo;
import com.genieLogiciele.sprint.repo.UserStoryRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserStorySerice {

    @Autowired
    UserStoryRepo userStoryRepo;
    @Autowired
    EpicRepo epicRepo;

    public   UserStory addUserStory(UserStory userStory,Long id)
    {
        Epic epic=epicRepo.findById(id).orElseThrow(()->new RuntimeException("Not Found Epic"));
        userStory.setEpic(epic);
        return userStoryRepo.save(userStory);
    }
    public  void deleteUserStory(Long id)
    {
        UserStory e = userStoryRepo.findById(id).orElseThrow(()->new RuntimeException("Entity Not Found"));
        userStoryRepo.delete(e);
    }

    public UserStory updateUserStory(UserStory userStory)
    {
        UserStory us=userStoryRepo.findById(userStory.getId()).orElseThrow();
        us.setTitre(userStory.getTitre());
        us.setDescription(userStory.getDescription());
        us.setStatus(userStory.getStatus());
        us.setPriorite(userStory.getPriorite());
        return userStoryRepo.save(us);
    }

    public List<UserStory> getAll()
    {
        return userStoryRepo.findAll();
    }


    public List<UserStory> getAllByEpic(Long id) {
        Epic epic= epicRepo.findById(id).orElseThrow(()->new EntityNotFound("Entity Not Found"));
        return userStoryRepo.findAllByEpic(epic);
    }
}
