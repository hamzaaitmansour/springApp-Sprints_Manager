package com.genieLogiciele.sprint.repo;

import com.genieLogiciele.sprint.entities.SprintBacklog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintBacklogRepo extends JpaRepository<SprintBacklog,Long> {
}
