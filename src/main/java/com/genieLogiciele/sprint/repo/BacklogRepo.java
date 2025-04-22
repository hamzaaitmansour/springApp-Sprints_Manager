package com.genieLogiciele.sprint.repo;

import com.genieLogiciele.sprint.entities.ProductBacklog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BacklogRepo extends JpaRepository<ProductBacklog,Long> {
}
