package ru.gb.chat.response;

import ru.gb.chat.server.ServerStatus;

public class ServerResponse {
    ServerStatus status;

    public ServerResponse(ServerStatus status) {
        this.status = status;
    }

    public ServerStatus getStatus() {
        return status;
    }
}
