package ru.gb.chat.server;

import ru.gb.chat.client.ClientData;
import ru.gb.chat.exceptions.ServerException;

import java.util.List;
import java.util.Set;

public interface ClientDB {
    void saveClient(ClientData clientData) throws ServerException;

    ClientData getDefaultClient(int clientId) throws ServerException;

    List<ClientData> getAllClients() throws ServerException;
}