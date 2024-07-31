package ru.gb.chat.response;

import ru.gb.chat.server.ServerStatus;

public class SendClientResponse extends ServerResponse{
    public SendClientResponse(ServerStatus status) {
        super(status);
    }
}
