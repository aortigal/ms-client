package com.bank.msclient.services;

import com.bank.msclient.handler.ResponseHandler;
import com.bank.msclient.models.documents.Client;
import reactor.core.publisher.Mono;

public interface ClientService {
    Mono<ResponseHandler> findAll();

    Mono<ResponseHandler> find(String id);

    Mono<ResponseHandler> create(Client client);

    Mono<ResponseHandler> update(String id,Client cli);

    Mono<ResponseHandler> delete(String id);
}
