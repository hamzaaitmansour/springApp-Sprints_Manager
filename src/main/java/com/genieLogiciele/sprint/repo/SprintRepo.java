package com.genieLogiciele.sprint.repo;

import com.genieLogiciele.sprint.entities.Sprint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SprintRepo extends JpaRepository<Sprint, Long> {
}
