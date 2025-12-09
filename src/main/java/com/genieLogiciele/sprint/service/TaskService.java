package com.genieLogiciele.sprint.service;
import com.genieLogiciele.sprint.entities.TaskSprint;
import com.genieLogiciele.sprint.entities.UserStory;
import com.genieLogiciele.sprint.exception.EntityNotFound;
import com.genieLogiciele.sprint.repo.TaskRepo;
import com.genieLogiciele.sprint.repo.UserStoryRepo;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {
    private final TaskRepo taskRepo;
    private final UserStoryRepo userStoryRepo;

    public TaskService(TaskRepo taskRepo, UserStoryRepo userStoryRepo) {
        this.taskRepo = taskRepo;
        this.userStoryRepo = userStoryRepo;
    }

    public TaskSprint addTask(TaskSprint taskSprint ,Long id )
       {
            UserStory userStory=userStoryRepo.findById(id).orElseThrow(()->new EntityNotFound("UserStory"));
            taskSprint.setUserStory(userStory);
           return taskRepo.save(taskSprint);
       }

       public TaskSprint getTask(Long id)
       {
           return taskRepo.findById(id).orElseThrow(()->new EntityNotFound("Not Found Task"));
       }
       public List<TaskSprint> getAllByUserStory(Long id)
       {
           UserStory userStory=userStoryRepo.findById(id).orElseThrow(()->new EntityNotFound("UserStory"));
           return taskRepo.findByUserStory(userStory);
       }


}
