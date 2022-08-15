package com.bank.msclient.controllers;

import com.bank.msclient.handler.ResponseHandler;
import com.bank.msclient.models.dao.ClientDao;
import com.bank.msclient.models.documents.Client;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import javax.validation.Valid;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/api/client")
public class ClientRestController
{
    @Autowired
    private ClientDao dao;
    private static final Logger log = LoggerFactory.getLogger(ClientRestController.class);

    @GetMapping
    public Mono<ResponseEntity<Object>> findAll()
    {
        log.info("[INI] findAll Client");
        return dao.findAll()
                .doOnNext(client -> log.info(client.toString()))
                .collectList()
                .map(clients -> ResponseHandler.response("Done", HttpStatus.OK, clients))
                .onErrorResume(error -> Mono.just(ResponseHandler.response(error.getMessage(), HttpStatus.BAD_REQUEST, null)))
                .doFinally(fin -> log.info("[END] findAll Client"));
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Object>> find(@PathVariable String id)
    {
        log.info("[INI] find Client");
        return dao.findById(id)
                .doOnNext(client -> log.info(client.toString()))
                .map(client -> ResponseHandler.response("Done", HttpStatus.OK, client))
                .onErrorResume(error -> Mono.just(ResponseHandler.response(error.getMessage(), HttpStatus.BAD_REQUEST, null)))
                .doFinally(fin -> log.info("[END] find Client"));
    }

    @PostMapping
    public Mono<ResponseEntity<Object>> create(@Valid @RequestBody Client client)
    {
        log.info("[INI] create Client");
        if(client.getClientData()!=null)
        {
            client.setClientDataId(new ObjectId().toString());
            client.setDateRegister(LocalDateTime.now());
            return dao.save(client)
                    .doOnNext(c -> {
                        log.info(c.toString());
                    })
                    .map(c -> ResponseHandler.response("Done", HttpStatus.OK, c))
                    .onErrorResume(error -> Mono.just(ResponseHandler.response(error.getMessage(), HttpStatus.BAD_REQUEST, null)))
                    .doFinally(fin -> log.info("[END] create Client"));
        }
        else
        {
            return Mono.just(ResponseHandler.response("Client's data required", HttpStatus.BAD_REQUEST, null));
        }
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Object>> update(@PathVariable("id") String id,@Valid @RequestBody Client cli)
    {
        log.info("[INI] update Client");
        return dao.existsById(id).flatMap(check -> {
            if (check){
                cli.setDateUpdate(LocalDateTime.now());
                return dao.save(cli)
                        .doOnNext(client -> log.info(client.toString()))
                        .map(client -> ResponseHandler.response("Done", HttpStatus.OK, client)                )
                        .onErrorResume(error -> Mono.just(ResponseHandler.response(error.getMessage(), HttpStatus.BAD_REQUEST, null)));
            }
            else
                return Mono.just(ResponseHandler.response("Not found", HttpStatus.NOT_FOUND, null));

        }).doFinally(fin -> log.info("[END] update Client"));
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Object>> delete(@PathVariable("id") String id)
    {
        log.info("[INI] delete Client");

        return dao.existsById(id).flatMap(check -> {
            if (check)
                return dao.deleteById(id).then(Mono.just(ResponseHandler.response("Done", HttpStatus.OK, null)));
            else
                return Mono.just(ResponseHandler.response("Not found", HttpStatus.NOT_FOUND, null));
        }).doFinally(fin -> log.info("[END] delete Client"));
    }
}

