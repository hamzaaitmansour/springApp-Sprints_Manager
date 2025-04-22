package com.genieLogiciele.sprint.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
public class SprintBacklog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;

    @ManyToMany
    private List<UserStory> userStories;

    @OneToOne
    @JoinColumn(name = "sprint_id")
    private Sprint sprint;

    @ManyToMany
    @JoinTable(
            name = "sprintbacklog_epic",
            joinColumns = @JoinColumn(name = "sprintbacklog_id"),
            inverseJoinColumns = @JoinColumn(name = "epic_id")
    )
    private List<Epic> epics; // Assurez-vous que ce nom est bien utilisé dans Epic
}
