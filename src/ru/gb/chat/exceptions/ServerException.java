package ru.gb.chat.exceptions;

import java.io.IOException;

public class ServerException extends IOException {
    public ServerException(String message) {
        super(message);
    }
}
