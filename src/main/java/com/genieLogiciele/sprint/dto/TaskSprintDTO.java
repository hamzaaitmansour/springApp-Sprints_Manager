package com.genieLogiciele.sprint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TaskSprintDTO {
    private Long id;
    private String title;
    private String description;
    private String status;
    private Long userStoryId;     // pour faire le lien sans charger toute l'entité
    private Long developpeurId;   // idem pour le développeur
}
