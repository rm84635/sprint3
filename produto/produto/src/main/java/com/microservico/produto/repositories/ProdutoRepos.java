package com.microservico.produto.repositories;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.microservico.produto.models.ProdutoModel;

public interface ProdutoRepos extends MongoRepository<ProdutoModel, Long> {

    @Query("{id: '?0'}")
    Optional<ProdutoModel> findById(Long id);

}