package ru.gb.chat.server;

import ru.gb.chat.client.ClientData;
import ru.gb.chat.exceptions.ServerException;
import ru.gb.chat.response.AllClientsResponse;
import ru.gb.chat.response.ClientResponse;
import ru.gb.chat.response.SendClientResponse;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerWindow extends JFrame implements Server{
    private static final int WIDTH = 800;
    private static final int HEIGHT = 300;
    private static final int POS_X = 500;
    private static final int POS_Y = 550;
    private static final Color NOT_ACTIVE_COLOR = Color.GRAY;
    private static final Color ACTIVE_COLOR = Color.GREEN;

    private final ClientDB clientDB;
    private final JButton startBtn = new JButton("Start");
    private final JButton stopBtn = new JButton("Stop");
    private final JTextArea log = new JTextArea();
    private boolean isServerWorking;

    public ServerWindow(ClientDB clientDB){
        this.clientDB = clientDB;
        this.isServerWorking = true;
        initialGui();
        setButtonsListener();
        colorizeCurrentButtons();
        initialClientsLog();
    }

    private void initialClientsLog() {
        AllClientsResponse response = getAllClientsResponse();
        if(response.getStatus().equals(ServerStatus.SUCCESS)){
            for(ClientData clientData: response.getClientDataList()){
                this.log.append(clientData + "\n");
            }
        }
    }

    private void setButtonsListener() {
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                isServerWorking = true;
                colorizeCurrentButtons();

            }
        });
        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                isServerWorking = false;
                colorizeCurrentButtons();
            }
        });
    }

    private void initialGui() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setBounds(POS_X, POS_Y, WIDTH, HEIGHT);
        setResizable(false);
        setTitle("Chat server");
        setAlwaysOnTop(true);

        add(log);

        JPanel panBottom = new JPanel(new GridLayout(1, 2));
        panBottom.add(startBtn);
        panBottom.add(stopBtn);

        add(panBottom, BorderLayout.SOUTH);
        colorizeCurrentButtons();
        setVisible(true);
    }

    private void colorizeCurrentButtons() {
        if(isServerWorking){
            startBtn.setBackground(NOT_ACTIVE_COLOR);
            stopBtn.setBackground(ACTIVE_COLOR);
        }else{
            startBtn.setBackground(ACTIVE_COLOR);
            stopBtn.setBackground(NOT_ACTIVE_COLOR);
        }
    }


    @Override
    public ClientResponse getDefaultClient(int clientId) {
        try {
            ServerStatus serverStatus;
            if (!this.isServerWorking) {
                serverStatus = ServerStatus.SERVER_NOT_FOUND;
                this.log.append(String.format("not get default client:server status:%s", serverStatus));
                return new ClientResponse(serverStatus, null);
            }


            ClientData clientData = clientDB.getDefaultClient(clientId);
            serverStatus = ServerStatus.SUCCESS;
            this.log.append(String.format("%s:get default client success%n", clientData.toString()));

            return new ClientResponse(serverStatus, clientData);
        }catch (ServerException e){
            return new ClientResponse(ServerStatus.ERROR, null);
        }
    }

    @Override
    public SendClientResponse sendClient(ClientData clientData) {
        ServerStatus serverStatus;
        if(!this.isServerWorking){
            serverStatus = ServerStatus.SERVER_NOT_FOUND;
            this.log.append(String.format("not send client server status %s%n", serverStatus));
            return new SendClientResponse(serverStatus);
        }

        try {
            clientDB.saveClient(clientData);
            serverStatus = ServerStatus.SUCCESS;
            this.log.append(String.format("send client success %s%n", clientData.toString()));
        } catch (ServerException e) {
            serverStatus = ServerStatus.ERROR;
            this.log.append(String.format("not send client server status %s%n", serverStatus));
        }

        return new SendClientResponse(serverStatus);
    }

    @Override
    public AllClientsResponse getAllClientsResponse() {
        try {
            java.util.List<ClientData> clientDataList = clientDB.getAllClients();
            return new AllClientsResponse(ServerStatus.SUCCESS, clientDataList);
        } catch (ServerException e) {
            return new AllClientsResponse(ServerStatus.ERROR, null);
        }
    }
}
