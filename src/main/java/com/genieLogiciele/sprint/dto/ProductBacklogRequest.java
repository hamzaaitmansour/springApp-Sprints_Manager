package com.genieLogiciele.sprint.dto;

import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ProductBacklogRequest {

    private String titre;
    private Long epic_id;
    private Long projet_id;
}
