package ru.gb.chat;

import ru.gb.chat.client.ClientGUI;
import ru.gb.chat.server.Server;
import ru.gb.chat.server.ServerWindow;

public class Main {
    public static void main(String[] args) {
        ServerWindow serverWindow = new ServerWindow();
        Server server = serverWindow.getServer();
        new ClientGUI(server);
        new ClientGUI(server);
    }
}