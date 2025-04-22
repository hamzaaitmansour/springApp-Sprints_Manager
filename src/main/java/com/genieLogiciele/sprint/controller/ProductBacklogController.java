package com.genieLogiciele.sprint.controller;

import com.genieLogiciele.sprint.dto.ProductBacklogRequest;
import com.genieLogiciele.sprint.dto.ProductBacklogResponse;
import com.genieLogiciele.sprint.entities.ProductBacklog;
import com.genieLogiciele.sprint.repo.ProjetRepo;
import com.genieLogiciele.sprint.service.BacklogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/productBacklog")
public class ProductBacklogController {
@Autowired
    private BacklogService backlogService;


@PostMapping("")
    public ResponseEntity<?> add(@RequestBody ProductBacklogRequest rq)
    {  backlogService.addProduct(rq);
       return new ResponseEntity<>(HttpStatus.CREATED);
    }

@GetMapping("/{id}")
   public ProductBacklogResponse getProduct(@PathVariable Long id) {
    return backlogService.getProduct(id);
}

 @PutMapping("/{id}")
    public ProductBacklog update(@PathVariable Long id, @RequestBody ProductBacklog rq) {
    return backlogService.updateBacklog(id,rq);
}



 @GetMapping("")
  public ResponseEntity<List<ProductBacklogResponse>> getProducts() {
    return new ResponseEntity<>(backlogService.getAll(),HttpStatus.OK);

}
}
