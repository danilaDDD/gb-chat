package ru.gb.chat.client;

import ru.gb.chat.response.ClientResponse;
import ru.gb.chat.response.SendClientResponse;
import ru.gb.chat.server.Server;
import ru.gb.chat.server.ServerStatus;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ClientGUI extends JFrame {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private static final int MAX_POS_X = 1400;
    private static final int MAX_POS_Y = 1400;

    private final Server server;
    private final JTextField log = new JTextField();

    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField ipAddressField = new JTextField();
    private final JTextField portField = new JTextField();
    private final JTextField loginField = new JTextField();
    private final JPasswordField passwordField = new JPasswordField();
    private final JButton loginBtn = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JTextField messageField = new JTextField();
    private final JButton sendBtn = new JButton("Send");

    private final Random random = new Random();

    private final int clientId;


    public ClientGUI(Server server, int clientId){
        this.server = server;
        this.clientId = clientId;
        initialGUI();
        initialDefaultData();

        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                submitClient();
            }
        });
    }

    private void submitClient() {
        ClientData clientData = getClientData();
        SendClientResponse sendResponse = this.server.sendClient(clientData);
        ServerStatus serverStatus = sendResponse.getStatus();

        if(serverStatus.equals(ServerStatus.SUCCESS)){
            this.messageField.setText("");
        }else{
            onServerError(serverStatus);
        }
    }

    private void onServerError(ServerStatus serverStatus) {
        this.log.setText(String.valueOf(serverStatus));
    }

    private ClientData getClientData() {
        return new ClientData(
                clientId,
                this.ipAddressField.getText(),
                this.portField.getText(),
                this.loginField.getText(),
                this.passwordField.getText(),
                this.messageField.getText()
        );
    }

    private void initialDefaultData() {
        ClientResponse clientResponse = server.getDefaultClient(this.clientId);
        if(clientResponse.getStatus().equals(ServerStatus.SUCCESS)){
            ClientData clientData = clientResponse.getClientData();
            setClientData(clientData);
        }else{
            onServerError(clientResponse.getStatus());
        }
    }

    private void setClientData(ClientData clientData) {
        this.ipAddressField.setText(clientData.getIpAddress());
        this.portField.setText(clientData.getPort());
        this.loginField.setText(clientData.getLogin());
        this.passwordField.setText(clientData.getPassword());
        this.messageField.setText(clientData.getMessage());
    }


    private void initialGUI() {
        setLocationRelativeTo(null);
        int[] position = getRandomPosition();
        setBounds(position[0], position[1], WIDTH, HEIGHT);
        setTitle(String.format("Chat client id: %s", clientId));

        panelTop.add(ipAddressField);
        panelTop.add(portField);
        panelTop.add(loginField);
        panelTop.add(passwordField);
        panelTop.add(loginBtn);
        add(panelTop, BorderLayout.NORTH);

        panelBottom.add(messageField, BorderLayout.CENTER);
        panelBottom.add(sendBtn, BorderLayout.EAST);
        add(panelBottom, BorderLayout.SOUTH);

        log.setEditable(false);
        JScrollPane scrollLog = new JScrollPane(log);
        add(scrollLog);

        setVisible(true);
    }

    private int[] getRandomPosition() {
        return new int[]{
                random.nextInt(MAX_POS_X),
                random.nextInt(MAX_POS_Y)
        };
    }
}
