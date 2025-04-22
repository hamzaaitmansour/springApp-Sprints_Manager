package com.genieLogiciele.sprint.repo;

import com.genieLogiciele.sprint.entities.TaskSprint;
import com.genieLogiciele.sprint.entities.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepo extends JpaRepository<TaskSprint,Long> {
    public List<TaskSprint> findByUserStory(UserStory userStory);
}
