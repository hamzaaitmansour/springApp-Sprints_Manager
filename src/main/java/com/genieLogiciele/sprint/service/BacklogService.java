package com.genieLogiciele.sprint.service;

import com.genieLogiciele.sprint.dto.ProductBacklogRequest;
import com.genieLogiciele.sprint.dto.ProductBacklogResponse;
import com.genieLogiciele.sprint.entities.Epic;
import com.genieLogiciele.sprint.entities.ProductBacklog;
import com.genieLogiciele.sprint.entities.Projet;
import com.genieLogiciele.sprint.repo.BacklogRepo;
import com.genieLogiciele.sprint.repo.EpicRepo;
import com.genieLogiciele.sprint.repo.ProjetRepo;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class BacklogService {
    private final BacklogRepo backlogRepo;
    private final EpicRepo ep;
    private final ProjetRepo projetRepo;

    public BacklogService(BacklogRepo backlogRepo, EpicRepo ep, ProjetRepo projetRepo) {
        this.backlogRepo = backlogRepo;
        this.ep = ep;
        this.projetRepo = projetRepo;
    }

    public ProductBacklog addProduct(ProductBacklogRequest rq)

    {   ProductBacklog product = new ProductBacklog();
        Epic epic=ep.findById(rq.getEpic_id()).orElseThrow();
        Projet projet = projetRepo.findById(rq.getProjet_id()).orElseThrow(()->new RuntimeException("Projet not found"));
        product.setEpic(epic);
        product.setProjet(projet);
        product.setTitre(rq.getTitre());
       return   backlogRepo.save(product);
    }
     public void deleteProduct(Long id)
    {

        backlogRepo.deleteById(id);
    }
    public List<ProductBacklogResponse> getAll()
    {
        List<ProductBacklog> productBacklogs= backlogRepo.findAll();
        List<ProductBacklogResponse> productBacklogResponses=new ArrayList<>();
        for (ProductBacklog productBacklog : productBacklogs)
        {
            ProductBacklogResponse productBacklogResponse=new ProductBacklogResponse();
            productBacklogResponse.setTitre(productBacklog.getTitre());
            productBacklogResponse.setProjet(productBacklog.getProjet().getNom());
            productBacklogResponse.setEpic(productBacklog.getEpic().getTitre());
            productBacklogResponses.add(productBacklogResponse);
        }
        return productBacklogResponses;
    }
    public ProductBacklog updateBacklog(Long id, ProductBacklog product)
    {
        ProductBacklog pr = backlogRepo.findById(id).orElseThrow(()->new RuntimeException(""));
        pr.setTitre(product.getTitre());


        return backlogRepo.save(pr);
    }
    public ProductBacklogResponse getProduct(Long id) {
        ProductBacklog pr = backlogRepo.findById(id).orElseThrow(()->new RuntimeException("Product not found"));
        ProductBacklogResponse prr = new ProductBacklogResponse();
        prr.setTitre(pr.getTitre());
        prr.setEpic(pr.getEpic().getTitre());
        prr.setProjet(pr.getProjet().getNom());
        return prr;
    }
}
