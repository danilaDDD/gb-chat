package ru.gb.chat.client;

import ru.gb.chat.models.Account;
import ru.gb.chat.models.Message;
import ru.gb.chat.response.ServerResponse;
import ru.gb.chat.server.Server;
import ru.gb.chat.server.ServerStatus;
import ru.gb.chat.server.ServerView;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class ClientGUI extends JFrame implements ClientView {
    private static final int WIDTH = 400;
    private static final int HEIGHT = 300;
    private static final int MAX_POS_X = 1400;
    private static final int MAX_POS_Y = 1400;

    private final JTextField log = new JTextField();

    private final JPanel panelTop = new JPanel(new GridLayout(2, 3));
    private final JTextField ipAddressField = new JTextField("127.0.0.1");
    private final JTextField portField = new JTextField("8080");
    private final JTextField loginField = new JTextField("ivanov");
    private final JPasswordField passwordField = new JPasswordField("float123");
    private final JButton loginBtn = new JButton("Login");

    private final JPanel panelBottom = new JPanel(new BorderLayout());
    private final JTextField messageField = new JTextField();
    private final JButton sendBtn = new JButton("Send");

    private final Random random = new Random();

    private final Client client;

    private java.util.List<String> messages;


    public ClientGUI(Server server){
        this.client = new Client(server, this);
        initialGUI();

        sendBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                Message message = getMessageObject();
                client.sendMessage(message);
            }
        });

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                String login = loginField.getText();
                String password = passwordField.getText();

                client.connectToServer(login, password);
            }
        });
    }

    private void onServerError(ServerStatus serverStatus) {
        this.log.setText(String.valueOf(serverStatus));
    }

    private Message getMessageObject() {
        return new Message(
                this.ipAddressField.getText(),
                this.portField.getText(),
                this.messageField.getText()
        );
    }


    private void initialGUI() {
        setLocationRelativeTo(null);
        int[] position = getRandomPosition();
        setBounds(position[0], position[1], WIDTH, HEIGHT);
        setTitle("Chat client id");

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

    @Override
    public void showMessage(String text) {
        messages.add(text);

        StringBuilder stringBuilder = new StringBuilder();
        for(String msg: messages)
            stringBuilder.append(msg).append("\n");

        messageField.setText(stringBuilder.toString());
    }

    @Override
    public void onConnectToServer(Account account) {
        panelTop.setVisible(false);
    }

    @Override
    public void onDisconnect() {
        log("Disconnect to server");
    }

    @Override
    public void onServerError(ServerResponse response) {
        log(response.getStatus().toString());
    }

    @Override
    public void log(String msg) {
        this.log.setText(msg);
    }
}
