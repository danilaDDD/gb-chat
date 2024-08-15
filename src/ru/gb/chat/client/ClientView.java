package ru.gb.chat.client;

import ru.gb.chat.models.Account;
import ru.gb.chat.response.ServerResponse;
import ru.gb.chat.server.ServerStatus;

public interface ClientView {
    void showMessage(String text);

    void onConnectToServer(Account account);

    void onDisconnect();

    void onServerError(ServerResponse response);

    void log(String serverNotConnected);
}
