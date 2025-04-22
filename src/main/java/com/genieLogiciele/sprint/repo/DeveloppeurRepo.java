package com.genieLogiciele.sprint.repo;

import com.genieLogiciele.sprint.entities.Developpeur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeveloppeurRepo extends JpaRepository<Developpeur, Long> {

}
