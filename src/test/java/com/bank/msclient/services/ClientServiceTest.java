package com.bank.msclient.services;

import com.bank.msclient.handler.ResponseHandler;
import com.bank.msclient.mocks.ClientMock;
import com.bank.msclient.models.dao.ClientDao;
import com.bank.msclient.models.documents.Client;
import com.bank.msclient.services.impl.ClientServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

@ExtendWith(SpringExtension.class)
public class ClientServiceTest {

    @InjectMocks
    private ClientServiceImpl clientService;

    @Mock
    private ClientDao dao;

    @Test
    void findAllTest() {

        Client client = ClientMock.random();

        Mockito.when(dao.findAll()).thenReturn(Flux.just(client));

        Mono<ResponseHandler> responseHandlerMono = clientService.findAll();

        StepVerifier.create(responseHandlerMono)
                .expectNextMatches(response -> response.getData() !=null)
                .verifyComplete();
    }

    @Test
    void createTest() {

        Client client = ClientMock.random();

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(client);

        //Mockito.when(dao.save(active)).thenReturn(Mono.just(active));
        Mockito.when(dao.save(client)).thenReturn(Mono.just(client));
    }

    @Test
    void findTest() {

        Client client = ClientMock.random();

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(client);

        Mockito.when(dao.findById("62edbc767ba3a05551fb10d6"))
                .thenReturn(Mono.just(client));

        Mono<ResponseHandler> responseHandlerMono = clientService.find("62edbc767ba3a05551fb10d6");

        StepVerifier.create(responseHandlerMono)
                .expectNextMatches(response -> response.getData() !=null)
                .verifyComplete();
    }

    @Test
    void updateTest() {

        Client client = ClientMock.random();

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(client);

        Mockito.when(dao.existsById("62edbc767ba3a05551fb10d6"))
                .thenReturn(Mono.just(true));

        Mockito.when(dao.save(client))
                .thenReturn(Mono.just(client));

        Mono<ResponseHandler> responseHandlerMono = clientService
                .update("62edbc767ba3a05551fb10d6", client);

        StepVerifier.create(responseHandlerMono)
                .expectNextMatches(response -> response.getData() !=null)
                .verifyComplete();
    }

    @Test
    void updateFoundTest() {

        Client client = ClientMock.random();

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(client);

        Mockito.when(dao.existsById("62edbc767ba3a05551fb10d6"))
                .thenReturn(Mono.just(false));

        Mono<ResponseHandler> responseHandlerMono = clientService
                .update("62edbc767ba3a05551fb10d6", client);

        StepVerifier.create(responseHandlerMono)
                .expectNextMatches(response -> response.getMessage().equals("Not found"))
                .verifyComplete();
    }

    @Test
    void deleteTest() {

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(null);

        Mockito.when(dao.existsById("62edbc767ba3a05551fb10d6"))
                .thenReturn(Mono.just(true));

        Mockito.when(dao.deleteById("62edbc767ba3a05551fb10d6")).thenReturn(Mono.empty());

        Mono<ResponseHandler> responseHandlerMono = clientService
                .delete("62edbc767ba3a05551fb10d6");

        StepVerifier.create(responseHandlerMono)
                .expectNextMatches(response -> response.getMessage().equals("Done"))
                .verifyComplete();
    }

    @Test
    void deleteFoundTest() {

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(null);

        Mockito.when(dao.existsById("62edbc767ba3a05551fb10d6"))
                .thenReturn(Mono.just(false));

        Mono<ResponseHandler> responseHandlerMono = clientService
                .delete("62edbc767ba3a05551fb10d6");

        StepVerifier.create(responseHandlerMono)
                .expectNextMatches(response -> response.getMessage().equals("Not found"))
                .verifyComplete();
    }
}
