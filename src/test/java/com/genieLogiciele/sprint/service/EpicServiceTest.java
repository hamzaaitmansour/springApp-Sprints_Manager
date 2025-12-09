package com.genieLogiciele.sprint.service;

import com.genieLogiciele.sprint.entities.Epic;
import com.genieLogiciele.sprint.exception.EntityNotFound;
import com.genieLogiciele.sprint.repo.EpicRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EpicServiceTest {

    @Mock
    private EpicRepo epicRepo;

    @InjectMocks
    private EpicService epicService;

    private Epic epic;

    @BeforeEach
    void setUp() {
        epic = new Epic();
        epic.setId(1L);
        epic.setTitre("Epic Test");
        epic.setDescription("Description Epic Test");
    }

    @Test
    void testAddEpicSuccess() {
        System.out.println("🧪 Test: Ajout d'une epic avec succès");
        when(epicRepo.save(any(Epic.class))).thenReturn(epic);

        Epic result = epicService.addEpic(epic);

        assertNotNull(result);
        assertEquals(epic.getTitre(), result.getTitre());
        assertEquals(epic.getDescription(), result.getDescription());
        System.out.println("✅ Test réussi: Epic ajoutée - " + result.getTitre());
        verify(epicRepo, times(1)).save(any(Epic.class));
    }

    @Test
    void testAddEpicNull() {
        System.out.println("🧪 Test: Ajout d'une epic null");
        when(epicRepo.save(null)).thenReturn(epic);

        Epic result = epicService.addEpic(null);

        assertNotNull(result);
        System.out.println("✅ Test réussi: Gestion de null");
        verify(epicRepo, times(1)).save(null);
    }

    @Test
    void testUpdateEpicSuccess() {
        System.out.println("🧪 Test: Mise à jour d'une epic avec succès");
        Epic updatedEpic = new Epic();
        updatedEpic.setId(1L);
        updatedEpic.setTitre("Nouveau Titre Epic");
        updatedEpic.setDescription("Nouvelle Description Epic");

        when(epicRepo.findById(1L)).thenReturn(Optional.of(epic));
        when(epicRepo.save(any(Epic.class))).thenReturn(updatedEpic);

        Epic result = epicService.updateEpic(updatedEpic);

        assertNotNull(result);
        verify(epicRepo, times(1)).findById(1L);
        verify(epicRepo, times(1)).save(any(Epic.class));
        System.out.println("✅ Test réussi: Epic mise à jour");
    }

    @Test
    void testUpdateEpicNotFound() {
        System.out.println("🧪 Test: Mise à jour d'une epic inexistante");
        Epic updatedEpic = new Epic();
        updatedEpic.setId(999L);
        updatedEpic.setTitre("Nouveau Titre");

        when(epicRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFound.class, () -> epicService.updateEpic(updatedEpic));
        System.out.println("✅ Test réussi: Exception levée pour Epic non trouvée");
        verify(epicRepo, times(1)).findById(999L);
        verify(epicRepo, never()).save(any());
    }

    @Test
    void testDeleteEpicSuccess() {
        System.out.println("🧪 Test: Suppression d'une epic avec succès");
        when(epicRepo.findById(1L)).thenReturn(Optional.of(epic));

        epicService.deleteEpic(1L);

        System.out.println("✅ Test réussi: Epic supprimée");
        verify(epicRepo, times(1)).findById(1L);
        verify(epicRepo, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteEpicNotFound() {
        System.out.println("🧪 Test: Suppression d'une epic inexistante");
        when(epicRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFound.class, () -> epicService.deleteEpic(999L));
        System.out.println("✅ Test réussi: Exception levée pour Epic non trouvée");
        verify(epicRepo, times(1)).findById(999L);
        verify(epicRepo, never()).deleteById(anyLong());
    }

    @Test
    void testGetEpicSuccess() {
        System.out.println("🧪 Test: Récupération d'une epic par ID avec succès");
        when(epicRepo.findById(1L)).thenReturn(Optional.of(epic));

        Epic result = epicService.getEpic(1L);

        assertNotNull(result);
        assertEquals(epic.getId(), result.getId());
        assertEquals(epic.getTitre(), result.getTitre());
        System.out.println("✅ Test réussi: Epic récupérée - " + result.getTitre());
        verify(epicRepo, times(1)).findById(1L);
    }

    @Test
    void testGetEpicNotFound() {
        System.out.println("🧪 Test: Récupération d'une epic inexistante");
        when(epicRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFound.class, () -> epicService.getEpic(999L));
        System.out.println("✅ Test réussi: Exception levée pour Epic non trouvée");
        verify(epicRepo, times(1)).findById(999L);
    }

    @Test
    void testGetAllEpics() {
        System.out.println("🧪 Test: Récupération de toutes les epics");
        Epic epic2 = new Epic();
        epic2.setId(2L);
        epic2.setTitre("Epic Test 2");
        epic2.setDescription("Description Epic Test 2");

        List<Epic> epics = Arrays.asList(epic, epic2);
        when(epicRepo.findAll()).thenReturn(epics);

        List<Epic> result = epicService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(epic.getTitre(), result.get(0).getTitre());
        assertEquals(epic2.getTitre(), result.get(1).getTitre());
        System.out.println("✅ Test réussi: " + result.size() + " epics récupérées");
        verify(epicRepo, times(1)).findAll();
    }

    @Test
    void testGetAllEpicsEmpty() {
        System.out.println("🧪 Test: Récupération quand aucune epic n'existe");
        when(epicRepo.findAll()).thenReturn(Arrays.asList());

        List<Epic> result = epicService.getAll();

        assertNotNull(result);
        assertEquals(0, result.size());
        System.out.println("✅ Test réussi: Liste vide retournée");
        verify(epicRepo, times(1)).findAll();
    }

    @Test
    void testUpdateEpicWithProductBacklog() {
        System.out.println("🧪 Test: Mise à jour d'une epic avec ProductBacklog");
        Epic updatedEpic = new Epic();
        updatedEpic.setId(1L);
        updatedEpic.setTitre("Epic Avec Backlog");
        updatedEpic.setDescription("Description avec backlog");
        updatedEpic.setProductBacklog(null); // ou une ProductBacklog réelle

        when(epicRepo.findById(1L)).thenReturn(Optional.of(epic));
        when(epicRepo.save(any(Epic.class))).thenReturn(updatedEpic);

        Epic result = epicService.updateEpic(updatedEpic);

        assertNotNull(result);
        assertEquals(updatedEpic.getTitre(), result.getTitre());
        System.out.println("✅ Test réussi: Epic avec ProductBacklog mise à jour");
        verify(epicRepo, times(1)).findById(1L);
        verify(epicRepo, times(1)).save(any(Epic.class));
    }
}