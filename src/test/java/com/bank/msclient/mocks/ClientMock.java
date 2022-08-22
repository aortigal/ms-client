package com.bank.msclient.mocks;

import com.bank.msclient.models.documents.Client;
import com.bank.msclient.models.documents.Personal;
import com.bank.msclient.models.enums.ClientGenre;
import com.bank.msclient.models.enums.ClientType;

import java.util.UUID;

public class ClientMock {

    public static Client random(){
        Client client = new Client();
        client.setId(UUID.randomUUID().toString());
        client.setDocumentId(UUID.randomUUID().toString());
        client.setGenre(ClientGenre.MALE);
        client.setFirstname(UUID.randomUUID().toString());
        client.setLastName(UUID.randomUUID().toString());
        client.setPhoneNumber(UUID.randomUUID().toString());
        client.setEmail(UUID.randomUUID().toString());
        client.setType(ClientType.PERSONAL);

        Personal p = new Personal();
        p.setId(UUID.randomUUID().toString());
        p.setCurrentAccount(UUID.randomUUID().toString());
        p.setSavingAccount(UUID.randomUUID().toString());

        client.setClientData(p);
        return client;
    }
}
