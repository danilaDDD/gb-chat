package ru.gb.chat.repository;

import ru.gb.chat.exceptions.ServerException;
import ru.gb.chat.models.Message;

import java.util.List;

public interface MessageRepository {
    List<Message> getAll() throws ServerException;

    void save(Message msgObj) throws ServerException;
}
