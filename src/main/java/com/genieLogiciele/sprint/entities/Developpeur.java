package com.genieLogiciele.sprint.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class Developpeur extends UserApp {

    @OneToMany(mappedBy = "developpeur")
    private List<TaskSprint> taskSprints;
}
