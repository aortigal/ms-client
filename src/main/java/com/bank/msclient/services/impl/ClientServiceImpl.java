package com.bank.msclient.services.impl;

import com.bank.msclient.handler.ResponseHandler;
import com.bank.msclient.models.dao.ClientDao;
import com.bank.msclient.models.documents.Client;
import com.bank.msclient.services.ClientService;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Service
public class ClientServiceImpl implements ClientService {
    @Autowired
    private ClientDao dao;
    private static final Logger log = LoggerFactory.getLogger(ClientServiceImpl.class);

    @Override
    public Mono<ResponseHandler> findAll() {
        log.info("[INI] findAll Client");
        return dao.findAll()
                .doOnNext(client -> log.info(client.toString()))
                .collectList()
                .map(clients -> new ResponseHandler("Done", HttpStatus.OK, clients))
                .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)))
                .doFinally(fin -> log.info("[END] findAll Client"));
    }

    @Override
    public Mono<ResponseHandler> find(String id) {
        log.info("[INI] find Client");
        return dao.findById(id)
                .doOnNext(client -> log.info(client.toString()))
                .map(client -> new ResponseHandler("Done", HttpStatus.OK, client))
                .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)))
                .doFinally(fin -> log.info("[END] find Client"));
    }

    @Override
    public Mono<ResponseHandler> create(Client client) {
        log.info("[INI] create Client");
        if(client.getClientData()!=null)
        {
            client.setClientDataId(new ObjectId().toString());
            client.setDateRegister(LocalDateTime.now());
            return dao.save(client)
                    .doOnNext(c -> {
                        log.info(c.toString());
                    })
                    .map(c -> new ResponseHandler("Done", HttpStatus.OK, c))
                    .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)))
                    .doFinally(fin -> log.info("[END] create Client"));
        }
        else
        {
            return Mono.just(new ResponseHandler("Client's data required", HttpStatus.BAD_REQUEST, null));
        }
    }

    @Override
    public Mono<ResponseHandler> update(String id,Client cli) {
        log.info("[INI] update Client");
        return dao.existsById(id).flatMap(check -> {
            if (check){
                cli.setDateUpdate(LocalDateTime.now());
                return dao.save(cli)
                        .doOnNext(client -> log.info(client.toString()))
                        .map(client -> new ResponseHandler("Done", HttpStatus.OK, client)                )
                        .onErrorResume(error -> Mono.just(new ResponseHandler(error.getMessage(), HttpStatus.BAD_REQUEST, null)));
            }
            else
                return Mono.just(new ResponseHandler("Not found", HttpStatus.NOT_FOUND, null));

        }).doFinally(fin -> log.info("[END] update Client"));
    }

    @Override
    public Mono<ResponseHandler> delete(String id) {
        log.info("[INI] delete Client");

        return dao.existsById(id).flatMap(check -> {
            if (check)
                return dao.deleteById(id).then(Mono.just(new ResponseHandler("Done", HttpStatus.OK, null)));
            else
                return Mono.just(new ResponseHandler("Not found", HttpStatus.NOT_FOUND, null));
        }).doFinally(fin -> log.info("[END] delete Client"));
    }
}
