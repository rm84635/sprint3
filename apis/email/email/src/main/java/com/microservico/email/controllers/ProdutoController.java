package com.microservico.email.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.microservico.email.models.ProdutoModel;
import com.microservico.email.repositories.ProdutoRepos;

@RestController
@RequestMapping("/produto")
public class ProdutoController {
    
    @Autowired
    private ProdutoRepos produtoRepos;

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<ProdutoModel> findById(@PathVariable String id){
        Optional<ProdutoModel> model = produtoRepos.findById(id);
        return new ResponseEntity<ProdutoModel>(model.get(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<ProdutoModel> salvar(@RequestBody ProdutoModel model){
        Optional<ProdutoModel> modelExistente = produtoRepos.findById(model.getId());

        if(modelExistente.isPresent()) {
            return new ResponseEntity("ja existe", HttpStatus.BAD_REQUEST);
        }

        produtoRepos.save(model);
        return new ResponseEntity<ProdutoModel>(model, HttpStatus.CREATED);
    }

}
