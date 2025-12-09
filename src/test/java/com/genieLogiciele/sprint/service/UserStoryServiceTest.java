package com.genieLogiciele.sprint.service;

import com.genieLogiciele.sprint.entities.Epic;
import com.genieLogiciele.sprint.entities.UserStory;
import com.genieLogiciele.sprint.exception.EntityNotFound;
import com.genieLogiciele.sprint.models.Priorite;
import com.genieLogiciele.sprint.models.Status;
import com.genieLogiciele.sprint.repo.EpicRepo;
import com.genieLogiciele.sprint.repo.UserStoryRepo;
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
class UserStoryServiceTest {

    @Mock
    private UserStoryRepo userStoryRepo;

    @Mock
    private EpicRepo epicRepo;

    @InjectMocks
    private UserStorySerice userStoryService;

    private UserStory userStory;
    private Epic epic;

    @BeforeEach
    void setUp() {
        epic = new Epic();
        epic.setId(1L);
        epic.setTitre("Epic Test");

        userStory = new UserStory();
        userStory.setId(1L);
        userStory.setTitre("User Story Test");
        userStory.setDescription("Description Test");
        userStory.setStatus(Status.TO_DO);
        userStory.setPriorite(Priorite.Must_Have);
        userStory.setEpic(epic);
    }

    @Test
    void testAddUserStorySuccess() {
        System.out.println("🧪 Test: Ajout d'une user story avec succès");
        when(epicRepo.findById(1L)).thenReturn(Optional.of(epic));
        when(userStoryRepo.save(any(UserStory.class))).thenReturn(userStory);

        UserStory result = userStoryService.addUserStory(userStory, 1L);

        assertNotNull(result);
        assertEquals(userStory.getTitre(), result.getTitre());
        assertEquals(epic.getId(), result.getEpic().getId());
        System.out.println("✅ Test réussi: User Story ajoutée - " + result.getTitre());
        verify(epicRepo, times(1)).findById(1L);
        verify(userStoryRepo, times(1)).save(any(UserStory.class));
    }

    @Test
    void testAddUserStoryEpicNotFound() {
        System.out.println("🧪 Test: Epic non trouvée lors de l'ajout d'une user story");
        when(epicRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userStoryService.addUserStory(userStory, 1L));
        System.out.println("✅ Test réussi: Exception levée pour Epic non trouvée");
        verify(epicRepo, times(1)).findById(1L);
        verify(userStoryRepo, never()).save(any());
    }

    @Test
    void testDeleteUserStorySuccess() {
        System.out.println("🧪 Test: Suppression d'une user story avec succès");
        when(userStoryRepo.findById(1L)).thenReturn(Optional.of(userStory));

        userStoryService.deleteUserStory(1L);

        System.out.println("✅ Test réussi: User Story supprimée");
        verify(userStoryRepo, times(1)).findById(1L);
        verify(userStoryRepo, times(1)).delete(userStory);
    }

    @Test
    void testDeleteUserStoryNotFound() {
        System.out.println("🧪 Test: Suppression d'une user story inexistante");
        when(userStoryRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> userStoryService.deleteUserStory(1L));
        System.out.println("✅ Test réussi: Exception levée pour User Story non trouvée");
        verify(userStoryRepo, times(1)).findById(1L);
        verify(userStoryRepo, never()).delete(any());
    }

    @Test
    void testUpdateUserStorySuccess() {
        System.out.println("🧪 Test: Mise à jour d'une user story avec succès");
        UserStory updatedUserStory = new UserStory();
        updatedUserStory.setId(1L);
        updatedUserStory.setTitre("Nouveau Titre");
        updatedUserStory.setDescription("Nouvelle Description");
        updatedUserStory.setStatus(Status.IN_PROGRESS);
        updatedUserStory.setPriorite(Priorite.Should_Have);

        when(userStoryRepo.findById(1L)).thenReturn(Optional.of(userStory));
        when(userStoryRepo.save(any(UserStory.class))).thenReturn(userStory);

        UserStory result = userStoryService.updateUserStory(updatedUserStory);

        assertNotNull(result);
        verify(userStoryRepo, times(1)).findById(1L);
        verify(userStoryRepo, times(1)).save(any(UserStory.class));
        System.out.println("✅ Test réussi: User Story mise à jour");
    }

    @Test
    void testUpdateUserStoryNotFound() {
        System.out.println("🧪 Test: Mise à jour d'une user story inexistante");
        UserStory updatedUserStory = new UserStory();
        updatedUserStory.setId(999L);

        when(userStoryRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> userStoryService.updateUserStory(updatedUserStory));
        System.out.println("✅ Test réussi: Exception levée pour User Story non trouvée");
        verify(userStoryRepo, times(1)).findById(999L);
        verify(userStoryRepo, never()).save(any());
    }

    @Test
    void testGetAllUserStories() {
        System.out.println("🧪 Test: Récupération de toutes les user stories");
        UserStory userStory2 = new UserStory();
        userStory2.setId(2L);
        userStory2.setTitre("User Story Test 2");
        userStory2.setDescription("Description Test 2");
        userStory2.setStatus(Status.DONE);
        userStory2.setPriorite(Priorite.Wont_Have);

        List<UserStory> userStories = Arrays.asList(userStory, userStory2);
        when(userStoryRepo.findAll()).thenReturn(userStories);

        List<UserStory> result = userStoryService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(userStory.getTitre(), result.get(0).getTitre());
        assertEquals(userStory2.getTitre(), result.get(1).getTitre());
        System.out.println("✅ Test réussi: " + result.size() + " user stories récupérées");
        verify(userStoryRepo, times(1)).findAll();
    }

    @Test
    void testGetAllUserStoriesEmpty() {
        System.out.println("🧪 Test: Récupération quand aucune user story n'existe");
        when(userStoryRepo.findAll()).thenReturn(Arrays.asList());

        List<UserStory> result = userStoryService.getAll();

        assertNotNull(result);
        assertEquals(0, result.size());
        System.out.println("✅ Test réussi: Liste vide retournée");
        verify(userStoryRepo, times(1)).findAll();
    }

    @Test
    void testGetAllByEpicSuccess() {
        System.out.println("🧪 Test: Récupération de toutes les user stories d'une epic");
        List<UserStory> userStories = Arrays.asList(userStory);
        when(epicRepo.findById(1L)).thenReturn(Optional.of(epic));
        when(userStoryRepo.findAllByEpic(epic)).thenReturn(userStories);

        List<UserStory> result = userStoryService.getAllByEpic(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(userStory.getTitre(), result.get(0).getTitre());
        System.out.println("✅ Test réussi: " + result.size() + " user stories trouvées pour l'epic");
        verify(epicRepo, times(1)).findById(1L);
        verify(userStoryRepo, times(1)).findAllByEpic(epic);
    }

    @Test
    void testGetAllByEpicEmpty() {
        System.out.println("🧪 Test: Récupération quand aucune user story n'existe pour une epic");
        when(epicRepo.findById(1L)).thenReturn(Optional.of(epic));
        when(userStoryRepo.findAllByEpic(epic)).thenReturn(Arrays.asList());

        List<UserStory> result = userStoryService.getAllByEpic(1L);

        assertNotNull(result);
        assertEquals(0, result.size());
        System.out.println("✅ Test réussi: Liste vide retournée pour l'epic");
        verify(epicRepo, times(1)).findById(1L);
        verify(userStoryRepo, times(1)).findAllByEpic(epic);
    }

    @Test
    void testGetAllByEpicNotFound() {
        System.out.println("🧪 Test: Récupération pour une epic inexistante");
        when(epicRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFound.class, () -> userStoryService.getAllByEpic(1L));
        System.out.println("✅ Test réussi: Exception levée pour Epic non trouvée");
        verify(epicRepo, times(1)).findById(1L);
        verify(userStoryRepo, never()).findAllByEpic(any());
    }
}