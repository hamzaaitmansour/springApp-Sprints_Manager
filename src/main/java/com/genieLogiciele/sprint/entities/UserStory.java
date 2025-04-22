package com.genieLogiciele.sprint.entities;

import com.genieLogiciele.sprint.models.Priorite;
import com.genieLogiciele.sprint.models.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserStory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private  String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Priorite priorite;

    @ManyToOne
    private Epic epic;


    @OneToMany
    private  List<TaskSprint> taskSprints;

    @ManyToMany
    private  List<SprintBacklog> sprintBacklogs;





}
