package com.genieLogiciele.sprint.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Epic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    @JsonIgnore
    @OneToOne(mappedBy = "epic")
    private ProductBacklog productBacklog;

    @JsonIgnore
    @OneToMany(mappedBy = "epic")
    private List<UserStory> userStories;
    @JsonIgnore
    @ManyToMany(mappedBy = "epics") // Assurez-vous que le nom "epics" est bien utilisé dans SprintBacklog
    private List<SprintBacklog> sprintBacklogs;
}
