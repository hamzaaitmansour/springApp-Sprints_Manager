package com.genieLogiciele.sprint.service;

import com.genieLogiciele.sprint.entities.SprintBacklog;
import com.genieLogiciele.sprint.entities.UserStory;
import com.genieLogiciele.sprint.entities.Sprint;
import com.genieLogiciele.sprint.entities.Epic;
import com.genieLogiciele.sprint.repo.SprintBacklogRepo;
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
class SprintBacklogServiceTest {

    @Mock
    private SprintBacklogRepo sprintBacklogRepo;

    @InjectMocks
    private SprintBacklogService sprintBacklogService;

    private SprintBacklog sprintBacklog;

    @BeforeEach
    void setUp() {
        Sprint sprint = new Sprint();
        sprint.setId(1L);

        Epic epic = new Epic();
        epic.setId(1L);
        epic.setTitre("Epic Test");

        UserStory userStory = new UserStory();
        userStory.setId(1L);
        userStory.setTitre("User Story Test");

        sprintBacklog = new SprintBacklog();
        sprintBacklog.setId(1L);
        sprintBacklog.setDescription("Description Sprint Backlog Test");
        sprintBacklog.setSprint(sprint);
        sprintBacklog.setUserStories(Arrays.asList(userStory));
        sprintBacklog.setEpics(Arrays.asList(epic));
    }

    @Test
    void testFindAllSprintBacklogs() {
        System.out.println("🧪 Test: Récupération de tous les sprint backlogs");
        Sprint sprint2 = new Sprint();
        sprint2.setId(2L);

        Epic epic2 = new Epic();
        epic2.setId(2L);
        epic2.setTitre("Epic Test 2");

        UserStory userStory2 = new UserStory();
        userStory2.setId(2L);
        userStory2.setTitre("User Story Test 2");

        SprintBacklog sprintBacklog2 = new SprintBacklog();
        sprintBacklog2.setId(2L);
        sprintBacklog2.setDescription("Description Sprint Backlog Test 2");
        sprintBacklog2.setSprint(sprint2);
        sprintBacklog2.setUserStories(Arrays.asList(userStory2));
        sprintBacklog2.setEpics(Arrays.asList(epic2));

        List<SprintBacklog> sprints = Arrays.asList(sprintBacklog, sprintBacklog2);
        when(sprintBacklogRepo.findAll()).thenReturn(sprints);

        List<SprintBacklog> result = sprintBacklogService.findAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(sprintBacklog.getDescription(), result.get(0).getDescription());
        assertEquals(sprintBacklog2.getDescription(), result.get(1).getDescription());
        System.out.println("✅ Test réussi: " + result.size() + " sprint backlogs récupérés");
        verify(sprintBacklogRepo, times(1)).findAll();
    }

    @Test
    void testFindAllSprintBacklogsEmpty() {
        System.out.println("🧪 Test: Récupération quand aucun sprint backlog n'existe");
        when(sprintBacklogRepo.findAll()).thenReturn(Arrays.asList());

        List<SprintBacklog> result = sprintBacklogService.findAll();

        assertNotNull(result);
        assertEquals(0, result.size());
        System.out.println("✅ Test réussi: Liste vide retournée");
        verify(sprintBacklogRepo, times(1)).findAll();
    }

    @Test
    void testFindByIdSuccess() {
        System.out.println("🧪 Test: Récupération d'un sprint backlog par ID avec succès");
        when(sprintBacklogRepo.findById(1L)).thenReturn(Optional.of(sprintBacklog));

        SprintBacklog result = sprintBacklogService.findById(1L);

        assertNotNull(result);
        assertEquals(sprintBacklog.getId(), result.getId());
        assertEquals(sprintBacklog.getDescription(), result.getDescription());
        assertEquals(1, result.getUserStories().size());
        assertEquals(1, result.getEpics().size());
        assertEquals(1L, result.getSprint().getId());
        System.out.println("✅ Test réussi: Sprint backlog récupéré avec " + result.getUserStories().size() + " user stories");
        verify(sprintBacklogRepo, times(1)).findById(1L);
    }

    @Test
    void testFindByIdNotFound() {
        System.out.println("🧪 Test: Récupération d'un sprint backlog inexistant");
        when(sprintBacklogRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> sprintBacklogService.findById(999L));
        System.out.println("✅ Test réussi: Exception levée pour sprint backlog non trouvé");
        verify(sprintBacklogRepo, times(1)).findById(999L);
    }

    @Test
    void testDeleteSprintBacklogSuccess() {
        System.out.println("🧪 Test: Suppression d'un sprint backlog avec succès");
        sprintBacklogService.deleteSprintBacklog(1L);

        System.out.println("✅ Test réussi: Sprint backlog supprimé");
        verify(sprintBacklogRepo, times(1)).deleteById(1L);
    }

    @Test
    void testAddSprintBacklogSuccess() {
        System.out.println("🧪 Test: Ajout d'un sprint backlog avec succès");
        when(sprintBacklogRepo.save(any(SprintBacklog.class))).thenReturn(sprintBacklog);

        SprintBacklog result = sprintBacklogService.addSprintbacklog(sprintBacklog);

        assertNotNull(result);
        assertEquals(sprintBacklog.getDescription(), result.getDescription());
        assertNotNull(result.getSprint());
        assertEquals(1, result.getUserStories().size());
        assertEquals(1, result.getEpics().size());
        System.out.println("✅ Test réussi: Sprint backlog ajouté avec " + result.getUserStories().size() + " user stories et " + result.getEpics().size() + " epics");
        verify(sprintBacklogRepo, times(1)).save(any(SprintBacklog.class));
    }

    @Test
    void testAddSprintBacklogWithMultipleUserStoriesAndEpics() {
        System.out.println("🧪 Test: Ajout d'un sprint backlog avec plusieurs user stories et epics");
        Sprint sprint = new Sprint();
        sprint.setId(2L);

        Epic epic1 = new Epic();
        epic1.setId(1L);
        epic1.setTitre("Epic 1");

        Epic epic2 = new Epic();
        epic2.setId(2L);
        epic2.setTitre("Epic 2");

        UserStory userStory1 = new UserStory();
        userStory1.setId(1L);

        UserStory userStory2 = new UserStory();
        userStory2.setId(2L);

        SprintBacklog sprintBacklog2 = new SprintBacklog();
        sprintBacklog2.setId(2L);
        sprintBacklog2.setDescription("Sprint avec multiples items");
        sprintBacklog2.setSprint(sprint);
        sprintBacklog2.setUserStories(Arrays.asList(userStory1, userStory2));
        sprintBacklog2.setEpics(Arrays.asList(epic1, epic2));

        when(sprintBacklogRepo.save(any(SprintBacklog.class))).thenReturn(sprintBacklog2);

        SprintBacklog result = sprintBacklogService.addSprintbacklog(sprintBacklog2);

        assertNotNull(result);
        assertEquals(2, result.getUserStories().size());
        assertEquals(2, result.getEpics().size());
        System.out.println("✅ Test réussi: Sprint backlog ajouté avec " + result.getUserStories().size() + " user stories et " + result.getEpics().size() + " epics");
        verify(sprintBacklogRepo, times(1)).save(any(SprintBacklog.class));
    }

    @Test
    void testFindByIdWithUserStoriesAndEpics() {
        System.out.println("🧪 Test: Récupération d'un sprint backlog avec user stories et epics");
        when(sprintBacklogRepo.findById(1L)).thenReturn(Optional.of(sprintBacklog));

        SprintBacklog result = sprintBacklogService.findById(1L);

        assertNotNull(result);
        assertNotNull(result.getUserStories());
        assertNotNull(result.getEpics());
        assertEquals(1, result.getUserStories().size());
        assertEquals(1, result.getEpics().size());
        System.out.println("✅ Test réussi: Sprint backlog récupéré avec relations");
        verify(sprintBacklogRepo, times(1)).findById(1L);
    }

    @Test
    void testDeleteMultipleSprintBacklogs() {
        System.out.println("🧪 Test: Suppression de plusieurs sprint backlogs");
        sprintBacklogService.deleteSprintBacklog(1L);
        sprintBacklogService.deleteSprintBacklog(2L);
        sprintBacklogService.deleteSprintBacklog(3L);

        System.out.println("✅ Test réussi: Plusieurs sprint backlogs supprimés");
        verify(sprintBacklogRepo, times(1)).deleteById(1L);
        verify(sprintBacklogRepo, times(1)).deleteById(2L);
        verify(sprintBacklogRepo, times(1)).deleteById(3L);
    }
}