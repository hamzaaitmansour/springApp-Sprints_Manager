package com.genieLogiciele.sprint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SprintBacklogDTO {
    private Long id;
    private String description;
    private List<Long> userStoryIds; // on ne met que les IDs pour alléger
    private Long sprintId;           // référence au sprint associé
    private List<Long> epicIds;      // IDs des epics associés
}
