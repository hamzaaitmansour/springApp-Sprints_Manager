package com.genieLogiciele.sprint.service;

import com.genieLogiciele.sprint.entities.Projet;
import com.genieLogiciele.sprint.exception.EntityAlreadyExist;
import com.genieLogiciele.sprint.repo.ProjetRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProjetService {

    @Autowired
    private ProjetRepo projetRepo;

    public Projet add(Projet projet)
    {
        if(projetRepo.existsByNom(projet.getNom()))
            throw new EntityAlreadyExist("Ce projet avec ce nom deja existe.");
        return projetRepo.save(projet);
    }

    public Projet getById(Long id)
    {
        return projetRepo.findById(id).orElseThrow(()->new RuntimeException("Not found"));
    }

    public void deleteProjet(Long id )
    {
        projetRepo.deleteById(id);
    }


    public List<Projet> findAll() {
        return projetRepo.findAll();
    }

    public Projet update(Long id, Projet projet) {
        Projet update = projetRepo.findById(id).orElseThrow(()->new RuntimeException("Not found"));
        update.setNom(projet.getNom());
        update.setDate_fin(projet.getDate_fin());
        update.setDate_creation(projet.getDate_creation());
        return projetRepo.save(update);
    }
}
