package com.genieLogiciele.sprint.service;

import com.genieLogiciele.sprint.entities.TaskSprint;
import com.genieLogiciele.sprint.entities.UserStory;
import com.genieLogiciele.sprint.exception.EntityNotFound;
import com.genieLogiciele.sprint.models.Priorite;
import com.genieLogiciele.sprint.models.Status;
import com.genieLogiciele.sprint.repo.TaskRepo;
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
class TaskServiceTest {

    @Mock
    private TaskRepo taskRepo;

    @Mock
    private UserStoryRepo userStoryRepo;

    @InjectMocks
    private TaskService taskService;

    private TaskSprint taskSprint;
    private UserStory userStory;

    @BeforeEach
    void setUp() {
        userStory = new UserStory();
        userStory.setId(1L);
        userStory.setTitre("User Story Test");
        userStory.setDescription("Description Test");
        userStory.setStatus(Status.TO_DO);
        userStory.setPriorite(Priorite.Must_Have);

        taskSprint = new TaskSprint();
        taskSprint.setId(1L);
        taskSprint.setTitle("Task Test");
        taskSprint.setDescription("Task Description Test");
        taskSprint.setStatus("TO_DO");
        taskSprint.setUserStory(userStory);
    }

    @Test
    void testAddTaskSuccess() {
        System.out.println("🧪 Test: Ajout d'une tâche avec succès");
        when(userStoryRepo.findById(1L)).thenReturn(Optional.of(userStory));
        when(taskRepo.save(any(TaskSprint.class))).thenReturn(taskSprint);

        TaskSprint result = taskService.addTask(taskSprint, 1L);

        assertNotNull(result);
        assertEquals(taskSprint.getTitle(), result.getTitle());
        assertEquals(taskSprint.getDescription(), result.getDescription());
        assertEquals("TO_DO", result.getStatus());
        assertEquals(userStory.getId(), result.getUserStory().getId());
        System.out.println("✅ Test réussi: Tâche ajoutée - " + result.getTitle());
        verify(userStoryRepo, times(1)).findById(1L);
        verify(taskRepo, times(1)).save(any(TaskSprint.class));
    }

    @Test
    void testAddTaskUserStoryNotFound() {
        System.out.println("🧪 Test: User Story non trouvée lors de l'ajout d'une tâche");
        when(userStoryRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFound.class, () -> taskService.addTask(taskSprint, 1L));
        System.out.println("✅ Test réussi: Exception levée pour User Story non trouvée");
        verify(userStoryRepo, times(1)).findById(1L);
        verify(taskRepo, never()).save(any());
    }

    @Test
    void testGetTaskSuccess() {
        System.out.println("🧪 Test: Récupération d'une tâche par ID avec succès");
        when(taskRepo.findById(1L)).thenReturn(Optional.of(taskSprint));

        TaskSprint result = taskService.getTask(1L);

        assertNotNull(result);
        assertEquals(taskSprint.getId(), result.getId());
        assertEquals(taskSprint.getTitle(), result.getTitle());
        assertEquals(taskSprint.getDescription(), result.getDescription());
        System.out.println("✅ Test réussi: Tâche récupérée - " + result.getTitle());
        verify(taskRepo, times(1)).findById(1L);
    }

    @Test
    void testGetTaskNotFound() {
        System.out.println("🧪 Test: Récupération d'une tâche inexistante");
        when(taskRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFound.class, () -> taskService.getTask(1L));
        System.out.println("✅ Test réussi: Exception levée pour tâche non trouvée");
        verify(taskRepo, times(1)).findById(1L);
    }

    @Test
    void testGetAllByUserStorySuccess() {
        System.out.println("🧪 Test: Récupération de toutes les tâches d'une user story");
        TaskSprint taskSprint2 = new TaskSprint();
        taskSprint2.setId(2L);
        taskSprint2.setTitle("Task Test 2");
        taskSprint2.setDescription("Task Description Test 2");
        taskSprint2.setStatus("IN_PROGRESS");
        taskSprint2.setUserStory(userStory);

        List<TaskSprint> tasks = Arrays.asList(taskSprint, taskSprint2);
        when(userStoryRepo.findById(1L)).thenReturn(Optional.of(userStory));
        when(taskRepo.findByUserStory(userStory)).thenReturn(tasks);

        List<TaskSprint> result = taskService.getAllByUserStory(1L);

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(taskSprint.getTitle(), result.get(0).getTitle());
        assertEquals(taskSprint2.getTitle(), result.get(1).getTitle());
        assertEquals("TO_DO", result.get(0).getStatus());
        assertEquals("IN_PROGRESS", result.get(1).getStatus());
        System.out.println("✅ Test réussi: " + result.size() + " tâches trouvées pour la user story");
        verify(userStoryRepo, times(1)).findById(1L);
        verify(taskRepo, times(1)).findByUserStory(userStory);
    }

    @Test
    void testGetAllByUserStoryEmpty() {
        System.out.println("🧪 Test: Récupération quand aucune tâche n'existe pour une user story");
        when(userStoryRepo.findById(1L)).thenReturn(Optional.of(userStory));
        when(taskRepo.findByUserStory(userStory)).thenReturn(Arrays.asList());

        List<TaskSprint> result = taskService.getAllByUserStory(1L);

        assertNotNull(result);
        assertEquals(0, result.size());
        System.out.println("✅ Test réussi: Liste vide retournée");
        verify(userStoryRepo, times(1)).findById(1L);
        verify(taskRepo, times(1)).findByUserStory(userStory);
    }

    @Test
    void testGetAllByUserStoryUserStoryNotFound() {
        System.out.println("🧪 Test: Récupération pour une user story inexistante");
        when(userStoryRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EntityNotFound.class, () -> taskService.getAllByUserStory(1L));
        System.out.println("✅ Test réussi: Exception levée pour User Story non trouvée");
        verify(userStoryRepo, times(1)).findById(1L);
        verify(taskRepo, never()).findByUserStory(any());
    }

    @Test
    void testAddTaskWithMultipleTasks() {
        System.out.println("🧪 Test: Ajout de plusieurs tâches");
        TaskSprint taskSprint2 = new TaskSprint();
        taskSprint2.setId(2L);
        taskSprint2.setTitle("Task Test 2");
        taskSprint2.setDescription("Task Description Test 2");
        taskSprint2.setStatus("DONE");
        taskSprint2.setUserStory(userStory);

        when(userStoryRepo.findById(1L)).thenReturn(Optional.of(userStory));
        when(taskRepo.save(any(TaskSprint.class))).thenReturn(taskSprint);

        TaskSprint result1 = taskService.addTask(taskSprint, 1L);
        assertNotNull(result1);
        assertEquals("TO_DO", result1.getStatus());

        when(taskRepo.save(any(TaskSprint.class))).thenReturn(taskSprint2);
        TaskSprint result2 = taskService.addTask(taskSprint2, 1L);
        assertNotNull(result2);
        assertEquals("DONE", result2.getStatus());

        System.out.println("✅ Test réussi: Plusieurs tâches ajoutées avec différents statuts");
        verify(userStoryRepo, times(2)).findById(1L);
        verify(taskRepo, times(2)).save(any(TaskSprint.class));
    }
}