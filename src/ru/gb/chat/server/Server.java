package ru.gb.chat.server;

import ru.gb.chat.client.ClientData;
import ru.gb.chat.response.AllClientsResponse;
import ru.gb.chat.response.ClientResponse;
import ru.gb.chat.response.SendClientResponse;

public interface Server {
    ClientResponse getDefaultClient(int clientId);

    SendClientResponse sendClient(ClientData clientData);

    AllClientsResponse getAllClientsResponse();
}
