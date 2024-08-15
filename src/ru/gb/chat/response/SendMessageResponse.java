package ru.gb.chat.response;

import ru.gb.chat.server.ServerStatus;

public class SendMessageResponse extends ServerResponse{
    public SendMessageResponse(ServerStatus status) {
        super(status);
    }
}
