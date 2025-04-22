package com.genieLogiciele.sprint.repo;


import com.genieLogiciele.sprint.entities.UserApp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAppRepo extends JpaRepository<UserApp,Long> {
    public UserApp findByEmail(String email);
}
