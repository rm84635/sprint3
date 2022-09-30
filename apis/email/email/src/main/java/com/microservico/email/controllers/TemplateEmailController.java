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

import com.microservico.email.models.EmailTemplate;
import com.microservico.email.repositories.TemplateEmailRepos;

@RestController
@RequestMapping("/template")
public class TemplateEmailController {
    
    @Autowired
    private TemplateEmailRepos templateRepos;

    @GetMapping("/{chave}")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<EmailTemplate> obterPorChave(@PathVariable String chave){
        Optional<EmailTemplate> template = templateRepos.obterPorChave(chave);
        return new ResponseEntity<EmailTemplate>(template.get(), HttpStatus.OK);
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<EmailTemplate> salvar(@RequestBody EmailTemplate template){
        Optional<EmailTemplate> templateExistente = templateRepos.obterPorChave(template.getChave());

        if(templateExistente.isPresent()) {
            return new ResponseEntity("Template ja existe", HttpStatus.BAD_REQUEST);
        }

        templateRepos.save(template);
        return new ResponseEntity<EmailTemplate>(template, HttpStatus.CREATED);
    }

}
