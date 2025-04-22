package com.genieLogiciele.sprint.dto;

import com.genieLogiciele.sprint.models.Priorite;
import com.genieLogiciele.sprint.models.Status;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserStoryDTO {
    private Long id;
    private String titre;
    private String description;
    private Status status;
    private Priorite priorite;

    private Long epicId; // pour ne pas renvoyer l'objet Epic complet

    private List<Long> taskSprintIds; // juste les IDs pour légèreté
    private List<Long> sprintBacklogIds; // idem
}
