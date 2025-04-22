package com.genieLogiciele.sprint.dto;
import com.genieLogiciele.sprint.models.SprintStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SprintDTO {
    private Long id;
    private String nom;
    private String objectif;
    private LocalDate date_debut;
    private LocalDate date_fin;
    private int duree;
    private SprintStatus status;
}
