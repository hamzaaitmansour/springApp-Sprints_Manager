package com.genieLogiciele.sprint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBacklogRequest {

    private String titre;
    private Long epic_id;
    private Long projet_id;
}
