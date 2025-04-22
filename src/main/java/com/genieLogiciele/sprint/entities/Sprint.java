package com.genieLogiciele.sprint.entities;

import com.genieLogiciele.sprint.models.SprintStatus;
import com.genieLogiciele.sprint.models.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String nom;
    private String objectif;
    private LocalDate date_debut;
    private LocalDate date_fin;
    private  int duree;
    @Enumerated(EnumType.STRING)
    private SprintStatus status;

    @OneToOne(mappedBy = "sprint")
    private SprintBacklog sprintBacklogList;
}
