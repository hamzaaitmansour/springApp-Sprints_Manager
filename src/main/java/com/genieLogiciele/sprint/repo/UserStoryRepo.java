package com.genieLogiciele.sprint.repo;

import com.genieLogiciele.sprint.entities.Epic;
import com.genieLogiciele.sprint.entities.UserStory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserStoryRepo extends JpaRepository<UserStory,Long> {
    public List<UserStory> findAllByEpic(Epic e );
}
