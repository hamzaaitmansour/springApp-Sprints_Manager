package com.genieLogiciele.sprint.service;

import com.genieLogiciele.sprint.entities.Epic;
import com.genieLogiciele.sprint.entities.ProductBacklog;
import com.genieLogiciele.sprint.exception.EntityNotFound;
import com.genieLogiciele.sprint.repo.BacklogRepo;
import com.genieLogiciele.sprint.repo.EpicRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EpicService {
    @Autowired
    private EpicRepo epicRepo;

    //@Autowired
    //private BacklogRepo backlogRepo;

    public Epic addEpic(Epic epic)
    {
        return epicRepo.save(epic);
    }
    public Epic updateEpic(Epic epic)
    {
        Epic e = getEpic(epic.getId());

        e.setTitre(epic.getTitre());
        e.setDescription(epic.getDescription());
        e.setProductBacklog(epic.getProductBacklog());
        //e.setUserStories(epic.getUserStories());

        return  epicRepo.save(epic);
    }
    public void deleteEpic(Long id)
    {
        Epic e = getEpic(id);

        epicRepo.deleteById(e.getId());

    }
    public Epic getEpic(Long id)
    {
        return  epicRepo.findById(id).orElseThrow(() ->  new EntityNotFound("Epic Not found"));
    }
    public List<Epic> getAll()
    {
        return epicRepo.findAll();
    }
}
