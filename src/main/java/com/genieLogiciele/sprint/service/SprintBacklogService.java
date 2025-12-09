package com.genieLogiciele.sprint.service;

import com.genieLogiciele.sprint.entities.SprintBacklog;
import com.genieLogiciele.sprint.repo.SprintBacklogRepo;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SprintBacklogService {

    private final SprintBacklogRepo sprintRepo;

    public SprintBacklogService(SprintBacklogRepo sprintRepo) {
        this.sprintRepo = sprintRepo;
    }

    public List<SprintBacklog> findAll() {

        return sprintRepo.findAll();
    }

    public SprintBacklog findById(long id) {
        return sprintRepo.findById(id).orElseThrow(()-> new RuntimeException("Sprint not found"));
    }
    public void deleteSprintBacklog(Long id)
    {
        sprintRepo.deleteById(id);
    }

    public SprintBacklog addSprintbacklog(SprintBacklog sprintBacklog)
    {

        return sprintRepo.save(sprintBacklog);
    }








}
