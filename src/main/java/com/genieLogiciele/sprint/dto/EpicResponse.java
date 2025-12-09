package com.genieLogiciele.sprint.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class EpicResponse {
    private String titre;
    private  String description;
    private Long id;
    private String productBacklogName;


}
