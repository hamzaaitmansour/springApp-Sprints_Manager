package com.genieLogiciele.sprint.repo;

import com.genieLogiciele.sprint.entities.Epic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EpicRepo extends JpaRepository<Epic,Long> {
}
