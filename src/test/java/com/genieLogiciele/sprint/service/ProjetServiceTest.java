package com.genieLogiciele.sprint.service;

import com.genieLogiciele.sprint.entities.Projet;
import com.genieLogiciele.sprint.exception.EntityAlreadyExist;
import com.genieLogiciele.sprint.repo.ProjetRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjetServiceTest {

    @Mock
    private ProjetRepo projetRepo;

    @InjectMocks
    private ProjetService projetService;

    private Projet projet;

    @BeforeEach
    void setUp() {
        projet = new Projet();
        projet.setId(1L);
        projet.setNom("Projet Test");
        projet.setDate_creation(LocalDate.parse("2025-01-01"));
        projet.setDate_fin(LocalDate.parse("2025-12-31"));
    }

    @Test
    void testAddProjetSuccess() {
        System.out.println("🧪 Test: Ajout d'un projet avec succès");
        when(projetRepo.existsByNom(projet.getNom())).thenReturn(false);
        when(projetRepo.save(any(Projet.class))).thenReturn(projet);

        Projet result = projetService.add(projet);

        assertNotNull(result);
        System.out.println("✅ Test réussi: Projet ajouté - " + result.getNom());
        assertEquals(projet.getNom(), result.getNom());
        verify(projetRepo, times(1)).existsByNom(projet.getNom());
        verify(projetRepo, times(1)).save(projet);
    }

    @Test
    void testAddProjetAlreadyExists() {
        when(projetRepo.existsByNom(projet.getNom())).thenReturn(true);

        assertThrows(EntityAlreadyExist.class, () -> projetService.add(projet));
        verify(projetRepo, times(1)).existsByNom(projet.getNom());
        verify(projetRepo, never()).save(any());
    }

    @Test
    void testGetByIdSuccess() {
        when(projetRepo.findById(1L)).thenReturn(Optional.of(projet));

        Projet result = projetService.getById(1L);

        assertNotNull(result);
        assertEquals(projet.getId(), result.getId());
        assertEquals(projet.getNom(), result.getNom());
        verify(projetRepo, times(1)).findById(1L);
    }

    @Test
    void testGetByIdNotFound() {
        when(projetRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> projetService.getById(1L));
        verify(projetRepo, times(1)).findById(1L);
    }

    @Test
    void testDeleteProjet() {
        projetService.deleteProjet(1L);

        verify(projetRepo, times(1)).deleteById(1L);
    }

    @Test
    void testFindAll() {
        Projet projet2 = new Projet();
        projet2.setId(2L);
        projet2.setNom("Projet Test 2");

        List<Projet> projets = Arrays.asList(projet, projet2);
        when(projetRepo.findAll()).thenReturn(projets);

        List<Projet> result = projetService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(projet.getNom(), result.get(0).getNom());
        assertEquals(projet2.getNom(), result.get(1).getNom());
        verify(projetRepo, times(1)).findAll();
    }

    @Test
    void testFindAllEmpty() {
        when(projetRepo.findAll()).thenReturn(Arrays.asList());

        List<Projet> result = projetService.findAll();

        assertNotNull(result);
        assertEquals(0, result.size());
        verify(projetRepo, times(1)).findAll();
    }

    @Test
    void testUpdateProjetSuccess() {
        Projet updatedProjet = new Projet();
        updatedProjet.setNom("Nouveau Nom");
        updatedProjet.setDate_creation(LocalDate.parse("2025-01-05"));
        updatedProjet.setDate_fin(LocalDate.parse("2025-11-30"));

        when(projetRepo.findById(1L)).thenReturn(Optional.of(projet));
        when(projetRepo.save(any(Projet.class))).thenReturn(projet);

        Projet result = projetService.update(1L, updatedProjet);

        assertNotNull(result);
        verify(projetRepo, times(1)).findById(1L);
        verify(projetRepo, times(1)).save(any(Projet.class));
    }

    @Test
    void testUpdateProjetNotFound() {
        when(projetRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> projetService.update(1L, projet));
        verify(projetRepo, times(1)).findById(1L);
        verify(projetRepo, never()).save(any());
    }
}