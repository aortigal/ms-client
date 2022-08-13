package com.bank.msclient.models.dao;

import com.bank.msclient.models.documents.Client;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ClientDao extends ReactiveMongoRepository<Client, String>{
}
