package com.genieLogiciele.sprint.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TaskSprint {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;
    private String description;
    private String title;
    private String status;

    @ManyToOne
    @JoinColumn(name = "id_userstory")
    private UserStory userStory;

    @ManyToOne
    private Developpeur developpeur;



}
