package ru.gb.chat.response;

import ru.gb.chat.client.ClientData;
import ru.gb.chat.server.ServerStatus;

import java.util.List;
import java.util.Set;

public class AllClientsResponse extends ServerResponse{
    private final List<ClientData> clientDataList;
    public AllClientsResponse(ServerStatus status, List<ClientData> clientDataList) {
        super(status);
        this.clientDataList = clientDataList;
    }

    public List<ClientData> getClientDataList() {
        return clientDataList;
    }
}
