package com.bank.msclient.controllers;

import com.bank.msclient.handler.ResponseHandler;
import com.bank.msclient.models.documents.Client;
import com.bank.msclient.services.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/client")
public class ClientRestController
{
    @Autowired
    private ClientService clientService;

    @GetMapping
    public Mono<ResponseHandler> findAll() {
        return clientService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseHandler> find(@PathVariable String id) {
        return clientService.find(id);
    }

    @PostMapping
    public Mono<ResponseHandler> create(@Valid @RequestBody Client client) {
        return clientService.create(client);
    }

    @PutMapping("/{id}")
    public Mono<ResponseHandler> update(@PathVariable("id") String id,@Valid @RequestBody Client cli) {
        return clientService.update(id,cli);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseHandler> delete(@PathVariable("id") String id) {
        return clientService.delete(id);
    }
}

