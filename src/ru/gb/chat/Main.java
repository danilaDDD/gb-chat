package ru.gb.chat;

import ru.gb.chat.client.ClientGUI;
import ru.gb.chat.server.FileClientDB;
import ru.gb.chat.server.Server;
import ru.gb.chat.server.ServerWindow;

public class Main {
    public static void main(String[] args) {
        ServerWindow serverWindow = new ServerWindow(new FileClientDB("clients.txt"));

        new ClientGUI(serverWindow, 1);
        new ClientGUI(serverWindow, 2);
    }
}