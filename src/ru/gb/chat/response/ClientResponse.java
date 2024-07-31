package ru.gb.chat.response;

import ru.gb.chat.client.ClientData;
import ru.gb.chat.server.ServerStatus;

public class ClientResponse extends ServerResponse{
    ClientData clientData;

    public ClientResponse(ServerStatus status, ClientData clientData) {
        super(status);
        this.clientData = clientData;
    }

    public ClientData getClientData() {
        return clientData;
    }
}
