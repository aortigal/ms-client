package com.bank.msclient.controllers;

import com.bank.msclient.handler.ResponseHandler;
import com.bank.msclient.mocks.ClientMock;
import com.bank.msclient.models.documents.Client;
import com.bank.msclient.services.ClientService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import org.springframework.web.reactive.function.BodyInserters;
import reactor.core.publisher.Mono;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebFluxTest(ClientRestController.class)
public class ClientRestControllerTest {

    @Autowired
    private WebTestClient webClient;

    @MockBean
    private ClientService clientService;

    @Test
    void findAllTest() {

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(null);

        when(clientService.findAll()).thenReturn(Mono.just(responseHandler));

        webClient
                .get().uri("/api/client")
                .exchange()
                .expectStatus()
                .isOk()
                .expectBody(ResponseHandler.class);

    }

    @Test
    void findByIdTest() {
        Client client = ClientMock.random();

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(client);

        Mockito
                .when(clientService.find("62edbc767ba3a05551fb10d6"))
                .thenReturn(Mono.just(responseHandler));

        webClient.get().uri("/api/client/{id}", "62edbc767ba3a05551fb10d6")
                .exchange()
                .expectStatus().isOk()
                .expectBody(ResponseHandler.class);

        Mockito.verify(clientService, times(1)).find("62edbc767ba3a05551fb10d6");
    }

    @Test
    void createTest() {

        Client client = ClientMock.random();

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(client);

        Mockito
                .when(clientService.create(client)).thenReturn(Mono.just(responseHandler));

        webClient
                .post()
                .uri("/api/client")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(client))
                .exchange()
                .expectStatus().isOk();

    }

    @Test
    void updateTest() {

        Client client = ClientMock.random();

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(client);

        Mockito
                .when(clientService.update("62edbc767ba3a05551fb10d6",client)).thenReturn(Mono.just(responseHandler));

        webClient
                .put()
                .uri("/api/client/{id}", "62edbc767ba3a05551fb10d6")
                .contentType(MediaType.APPLICATION_JSON)
                .body(BodyInserters.fromObject(client))
                .exchange()
                .expectStatus().isOk();
    }

    @Test
    void deleteTest() {

        ResponseHandler responseHandler = new ResponseHandler();
        responseHandler.setMessage("Ok");
        responseHandler.setStatus(HttpStatus.OK);
        responseHandler.setData(null);

        Mockito
                .when(clientService.delete("62edbc767ba3a05551fb10d6"))
                .thenReturn(Mono.just(responseHandler));

        webClient.delete().uri("/api/client/{id}", "62edbc767ba3a05551fb10d6")
                .exchange()
                .expectStatus().isOk();
    }
}
