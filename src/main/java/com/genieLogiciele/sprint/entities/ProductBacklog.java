package com.genieLogiciele.sprint.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductBacklog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titre;
    @OneToOne
    @JoinColumn(name = "epic_id")
    private Epic epic;
    @ManyToOne
    @JoinColumn(name = "projet_id")
    private Projet projet;
    }


