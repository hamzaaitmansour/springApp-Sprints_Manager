package com.genieLogiciele.sprint.repo;

import com.genieLogiciele.sprint.entities.Projet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjetRepo extends JpaRepository<Projet,Long> {
    boolean existsByNom(String nom);
}
