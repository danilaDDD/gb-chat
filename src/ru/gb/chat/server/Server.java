package ru.gb.chat.server;

import ru.gb.chat.client.Client;
import ru.gb.chat.exceptions.ServerException;
import ru.gb.chat.models.Account;
import ru.gb.chat.models.Message;
import ru.gb.chat.repository.*;
import ru.gb.chat.response.AccountResponse;
import ru.gb.chat.response.SendMessageResponse;

import java.util.ArrayList;
import java.util.List;

public class Server {
    private final List<Client> clients;
    private boolean isServerWorking;
    private final ServerView view;
    private final AccountRepository accountRepository = new AccountFileRepository();
    private final MessageRepository messageRepository = new MessageFileRepository();
    private final LogRepository logRepository = new LogRepository();

    public Server(boolean isServerWorking, ServerView view) {
        this.view =view;
        this.isServerWorking = isServerWorking;
        clients = new ArrayList<>();
    }

    public void setServerWorking(boolean serverWorking) {
        isServerWorking = serverWorking;
    }

    public boolean isServerWorking() {
        return isServerWorking;
    }

    public SendMessageResponse sendMessage(Message msgObj){
        try {
            if (isServerWorking()) {
                messageRepository.save(msgObj);

                for (Client client : clients)
                    client.sendMessage(msgObj);

                view.infoLog(String.format("Send message %s", msgObj));

                return new SendMessageResponse(ServerStatus.SUCCESS);
            } else {
                view.errorLog(String.format("Send message error %s. Server is stopped", msgObj));

                return new SendMessageResponse(ServerStatus.SERVER_NOT_FOUND);
            }
        } catch (ServerException e) {
            view.errorLog(String.format("error %s", e.getMessage()));
            return new SendMessageResponse(ServerStatus.ERROR);
        }

    }

    public AccountResponse connectClient(String login, String password) {
        try {
            if (isServerWorking()) {
                Account account = accountRepository.find(login, password);
                if (account != null) {
                    view.infoLog(String.format("find %s", account));
                } else {
                    account = new Account(login, password);
                    accountRepository.save(account);
                }

                return new AccountResponse(ServerStatus.SUCCESS, account);
            } else {
                view.errorLog(String.format("client %s connection error. Server is stopped", login));
                return new AccountResponse(ServerStatus.SERVER_NOT_FOUND, null);
            }
        } catch (ServerException e) {
            view.errorLog(String.format("error %s", e.getMessage()));
            return new AccountResponse(ServerStatus.ERROR, null);
        }
    }


        public void disconnectClient(Client client) {
        client.disconnect();
    }

    public List<Message> loadMessages() {
        try {
            return messageRepository.getAll();
        } catch (ServerException e) {
            view.errorLog(String.format("error %s", e.getMessage()));
            return new ArrayList<>();
        }
    }

    public String loadLog(){
        try {
            return logRepository.loadLog();
        } catch (ServerException e) {
            view.errorLog(String.format("error %s", e.getMessage()));
            return "";
        }
    }

    public void log(String msg){
        try {
            logRepository.saveLine(msg);
        } catch (ServerException e) {
            view.errorLog(String.format("error %s", e.getMessage()));
        }
    }
}
