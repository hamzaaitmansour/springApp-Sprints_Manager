package com.genieLogiciele.sprint.service;

import com.genieLogiciele.sprint.dto.ProductBacklogRequest;
import com.genieLogiciele.sprint.dto.ProductBacklogResponse;
import com.genieLogiciele.sprint.entities.Epic;
import com.genieLogiciele.sprint.entities.ProductBacklog;
import com.genieLogiciele.sprint.entities.Projet;
import com.genieLogiciele.sprint.repo.BacklogRepo;
import com.genieLogiciele.sprint.repo.EpicRepo;
import com.genieLogiciele.sprint.repo.ProjetRepo;
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
class BacklogServiceTest {

    @Mock
    private BacklogRepo backlogRepo;

    @Mock
    private EpicRepo epicRepo;

    @Mock
    private ProjetRepo projetRepo;

    @InjectMocks
    private BacklogService backlogService;

    private ProductBacklog productBacklog;
    private Epic epic;
    private Projet projet;
    private ProductBacklogRequest productBacklogRequest;

    @BeforeEach
    void setUp() {
        epic = new Epic();
        epic.setId(1L);
        epic.setTitre("Epic Test");

        projet = new Projet();
        projet.setId(1L);
        projet.setNom("Projet Test");

        productBacklog = new ProductBacklog();
        productBacklog.setId(1L);
        productBacklog.setTitre("Product Backlog Test");
        productBacklog.setEpic(epic);
        productBacklog.setProjet(projet);

        productBacklogRequest = new ProductBacklogRequest();
        productBacklogRequest.setEpic_id(1L);
        productBacklogRequest.setProjet_id(1L);
        productBacklogRequest.setTitre("Product Backlog Test");
    }

    @Test
    void testAddProductSuccess() {
        System.out.println("🧪 Test: Ajout d'un produit backlog avec succès");
        when(epicRepo.findById(1L)).thenReturn(Optional.of(epic));
        when(projetRepo.findById(1L)).thenReturn(Optional.of(projet));
        when(backlogRepo.save(any(ProductBacklog.class))).thenReturn(productBacklog);

        ProductBacklog result = backlogService.addProduct(productBacklogRequest);

        assertNotNull(result);
        assertEquals(productBacklog.getTitre(), result.getTitre());
        assertEquals(epic.getId(), result.getEpic().getId());
        assertEquals(projet.getId(), result.getProjet().getId());
        System.out.println("✅ Test réussi: Produit ajouté - " + result.getTitre());
        verify(epicRepo, times(1)).findById(1L);
        verify(projetRepo, times(1)).findById(1L);
        verify(backlogRepo, times(1)).save(any(ProductBacklog.class));
    }

    @Test
    void testAddProductEpicNotFound() {
        System.out.println("🧪 Test: Epic non trouvée lors de l'ajout");
        when(epicRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(Exception.class, () -> backlogService.addProduct(productBacklogRequest));
        System.out.println("✅ Test réussi: Exception levée pour Epic non trouvée");
        verify(epicRepo, times(1)).findById(1L);
    }

    @Test
    void testAddProductProjetNotFound() {
        System.out.println("🧪 Test: Projet non trouvé lors de l'ajout");
        when(epicRepo.findById(1L)).thenReturn(Optional.of(epic));
        when(projetRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> backlogService.addProduct(productBacklogRequest));
        System.out.println("✅ Test réussi: Exception levée pour Projet non trouvé");
        verify(epicRepo, times(1)).findById(1L);
        verify(projetRepo, times(1)).findById(1L);
    }

    @Test
    void testDeleteProduct() {
        System.out.println("🧪 Test: Suppression d'un produit backlog");
        backlogService.deleteProduct(1L);

        verify(backlogRepo, times(1)).deleteById(1L);
        System.out.println("✅ Test réussi: Produit supprimé");
    }

    @Test
    void testGetAllProducts() {
        System.out.println("🧪 Test: Récupération de tous les produits backlog");
        ProductBacklog productBacklog2 = new ProductBacklog();
        productBacklog2.setId(2L);
        productBacklog2.setTitre("Product Backlog Test 2");
        productBacklog2.setEpic(epic);
        productBacklog2.setProjet(projet);

        List<ProductBacklog> productBacklogs = Arrays.asList(productBacklog, productBacklog2);
        when(backlogRepo.findAll()).thenReturn(productBacklogs);

        List<ProductBacklogResponse> result = backlogService.getAll();

        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(productBacklog.getTitre(), result.get(0).getTitre());
        assertEquals(epic.getTitre(), result.get(0).getEpic());
        assertEquals(projet.getNom(), result.get(0).getProjet());
        System.out.println("✅ Test réussi: " + result.size() + " produits récupérés");
        verify(backlogRepo, times(1)).findAll();
    }

    @Test
    void testGetAllProductsEmpty() {
        System.out.println("🧪 Test: Récupération quand aucun produit n'existe");
        when(backlogRepo.findAll()).thenReturn(Arrays.asList());

        List<ProductBacklogResponse> result = backlogService.getAll();

        assertNotNull(result);
        assertEquals(0, result.size());
        System.out.println("✅ Test réussi: Liste vide retournée");
        verify(backlogRepo, times(1)).findAll();
    }

    @Test
    void testUpdateBacklogSuccess() {
        System.out.println("🧪 Test: Mise à jour d'un produit backlog");
        ProductBacklog updatedBacklog = new ProductBacklog();
        updatedBacklog.setTitre("Nouveau Titre");

        when(backlogRepo.findById(1L)).thenReturn(Optional.of(productBacklog));
        when(backlogRepo.save(any(ProductBacklog.class))).thenReturn(productBacklog);

        ProductBacklog result = backlogService.updateBacklog(1L, updatedBacklog);

        assertNotNull(result);
        verify(backlogRepo, times(1)).findById(1L);
        verify(backlogRepo, times(1)).save(any(ProductBacklog.class));
        System.out.println("✅ Test réussi: Produit mis à jour");
    }

    @Test
    void testUpdateBacklogNotFound() {
        System.out.println("🧪 Test: Mise à jour d'un produit inexistant");
        when(backlogRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> backlogService.updateBacklog(1L, productBacklog));
        System.out.println("✅ Test réussi: Exception levée pour produit non trouvé");
        verify(backlogRepo, times(1)).findById(1L);
        verify(backlogRepo, never()).save(any());
    }

    @Test
    void testGetProductSuccess() {
        System.out.println("🧪 Test: Récupération d'un produit par ID");
        when(backlogRepo.findById(1L)).thenReturn(Optional.of(productBacklog));

        ProductBacklogResponse result = backlogService.getProduct(1L);

        assertNotNull(result);
        assertEquals(productBacklog.getTitre(), result.getTitre());
        assertEquals(epic.getTitre(), result.getEpic());
        assertEquals(projet.getNom(), result.getProjet());
        System.out.println("✅ Test réussi: Produit récupéré - " + result.getTitre());
        verify(backlogRepo, times(1)).findById(1L);
    }

    @Test
    void testGetProductNotFound() {
        System.out.println("🧪 Test: Récupération d'un produit inexistant");
        when(backlogRepo.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> backlogService.getProduct(1L));
        System.out.println("✅ Test réussi: Exception levée pour produit non trouvé");
        verify(backlogRepo, times(1)).findById(1L);
    }
}