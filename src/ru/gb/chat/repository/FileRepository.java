package ru.gb.chat.repository;
import ru.gb.chat.exceptions.ServerException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FileRepository {
    private String filePath;

    public FileRepository(String filePath) {
        this.filePath = filePath;
    }

    public void saveString(String line) throws ServerException {
        try(FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(String.format("%s%n", line));
        } catch (IOException e) {
            throw new ServerException("client data not saved");
        }
    }

    public List<String> getAllLines() throws ServerException {
        List<String> lines = new ArrayList<>();

        try(Scanner scanner = new Scanner(new FileReader(this.filePath))){
            scanner.nextLine(); // проходит первую пустую строку
            while(scanner.hasNext()){
                lines.add(scanner.nextLine());
            }

            return lines;
        } catch (FileNotFoundException e) {
            throw new ServerException("System exception, data not loaded");
        }
    }
}
