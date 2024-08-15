package ru.gb.chat.client;

import ru.gb.chat.models.Account;
import ru.gb.chat.models.Message;
import ru.gb.chat.response.AccountResponse;
import ru.gb.chat.response.SendMessageResponse;
import ru.gb.chat.response.ServerResponse;
import ru.gb.chat.server.Server;
import ru.gb.chat.server.ServerStatus;

import java.util.List;
import java.util.stream.Collectors;

public class Client {
    Server server;
    ClientView view;
    boolean connected;
    Account account;

    public Client(Server server, ClientView view) {
        this.server = server;
        this.view = view;
    }

    List<String> loadMessages(){
        List<Message> messages = server.loadMessages();
        return messages.stream().map(Message::toStringMessage).collect(Collectors.toList());
    }

    public void connectToServer(String login, String password){
        AccountResponse response = server.connectClient(login, password);
        if(response.getStatus() == ServerStatus.SUCCESS){
            this.account = response.getAccount();
            view.onConnectToServer(this.account);
            connected = true;
        }else {
            onServerError(response);
        }
    }

    public void sendMessage(Message msgObj){
        if(!connected){
            view.log("Server not connected");
        }else {
            SendMessageResponse response = server.sendMessage(msgObj);
            if (response.getStatus() == ServerStatus.SUCCESS) {
                view.showMessage(msgObj.getMessage());
            } else {
                onServerError(response);
            }
        }
    }

    private void onServerError(ServerResponse response) {
        view.onServerError(response);
    }

    public void disconnect(){
        server.disconnectClient(this);
        view.onDisconnect();
    }


}
