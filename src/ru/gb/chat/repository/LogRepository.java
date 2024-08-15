package ru.gb.chat.repository;

import ru.gb.chat.exceptions.ServerException;

public class LogRepository {
    private FileRepository fileRepository = new FileRepository("log.txt");

    public String loadLog() throws ServerException {
        StringBuilder stringBuilder = new StringBuilder();
        for(String line: fileRepository.getAllLines())
            stringBuilder.append(line);

        return stringBuilder.toString();
    }

    public void saveLine(String line) throws ServerException {
        fileRepository.saveString(String.format("%s%n", line));
    }
}
