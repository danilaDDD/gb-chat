package ru.gb.chat.server;

import ru.gb.chat.client.ClientData;
import ru.gb.chat.exceptions.ServerException;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Set;

public class FileClientDB implements ClientDB{
    private String filePath;

    public FileClientDB(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public void saveClient(ClientData clientData) throws ServerException {
        try(FileWriter writer = new FileWriter(filePath, true)) {
            writer.write(String.format("%s%n", clientData));
        } catch (IOException e) {
            throw new ServerException("client data not saved");
        }
    }

    /*
    * Возвратить данные последнеко отправленного clientData с данным идентификатором
    * если его нет в базе, отправить дефолтного
     */
    @Override
    public ClientData getDefaultClient(int clientId) throws ServerException {
        ClientData lastClientData = null;
        List<ClientData> clients = getAllClients();
        for(ClientData clientData: clients){
            if(clientData.getClientId() == clientId){
                lastClientData = clientData;
            }
        }

        if(lastClientData != null) {
            return lastClientData;
        }

        return new ClientData(clientId, "127.0.0.1", "8080", "ivan_ivanov", "by27672266263");
    }

    @Override
    public List<ClientData> getAllClients() throws ServerException {
        List<ClientData> clientDataList = new ArrayList<>();
        try(Scanner scanner = new Scanner(new FileReader(this.filePath))){
            scanner.nextLine(); // проходит первую пустую строку
            while(scanner.hasNext()){
                ClientData clientData = ClientData.parseClientData(scanner.nextLine());
                clientDataList.add(clientData);
            }
        } catch (FileNotFoundException e) {
            throw new ServerException("System exception, clients data not loaded");
        }
        return clientDataList;
    }
}
