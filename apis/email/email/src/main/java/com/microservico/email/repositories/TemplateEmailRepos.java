package com.microservico.email.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.microservico.email.models.EmailTemplate;

public interface TemplateEmailRepos extends MongoRepository<EmailTemplate, String> {

    @Query("{chave: '?0'}")
    Optional<EmailTemplate> obterPorChave(String chave);
    
}
