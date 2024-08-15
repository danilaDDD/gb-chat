package ru.gb.chat.repository;

import ru.gb.chat.exceptions.ServerException;
import ru.gb.chat.models.Account;
import ru.gb.chat.models.Message;

import java.util.List;
import java.util.stream.Collectors;

public class MessageFileRepository implements MessageRepository{
    private final FileRepository fileRepository = new FileRepository("messages.txt");

    @Override
    public List<Message> getAll() throws ServerException {
        return fileRepository.getAllLines()
                .stream().map(Message::parseMessage).collect(Collectors.toList());
    }

    @Override
    public void save(Message msgObj) throws ServerException {
        fileRepository.saveString(msgObj + "\n");
    }
}
