package com.microservico.email.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.microservico.email.models.EmailEnviadoModel;

public interface EmailEnviadoRepos  extends MongoRepository<EmailEnviadoModel, String> {

}
