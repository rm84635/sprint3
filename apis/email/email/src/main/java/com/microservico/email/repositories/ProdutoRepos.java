package com.microservico.email.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.microservico.email.models.ProdutoModel;

public interface ProdutoRepos extends MongoRepository<ProdutoModel, String> {

    @Query("{id: '?0'}")
    Optional<ProdutoModel> findById(String id);

}