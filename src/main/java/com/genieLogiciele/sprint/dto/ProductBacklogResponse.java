package com.genieLogiciele.sprint.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductBacklogResponse {

    private Long id;
    private String titre;
    private String epic;
    private String projet;

}
