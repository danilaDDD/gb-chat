package ru.gb.chat.server;

import ru.gb.chat.exceptions.ServerException;
import ru.gb.chat.repository.LogRepository;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServerWindow extends JFrame implements ServerView{
    private static final int WIDTH = 800;
    private static final int HEIGHT = 300;
    private static final int POS_X = 500;
    private static final int POS_Y = 550;
    private static final Color NOT_ACTIVE_COLOR = Color.GRAY;
    private static final Color ACTIVE_COLOR = Color.GREEN;

    private final Server server;
    private final JButton startBtn = new JButton("Start");
    private final JButton stopBtn = new JButton("Stop");
    private final JTextArea logField = new JTextArea();

    public ServerWindow(){
        this.server = new Server(false, this);
        initialGui();
        setButtonsListener();
        colorizeCurrentButtons();
        logField.setText(server.loadLog());
    }

    private void setButtonsListener() {
        startBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                server.setServerWorking(true);
                colorizeCurrentButtons();

            }
        });
        stopBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                server.setServerWorking(false);
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

        add(logField);

        JPanel panBottom = new JPanel(new GridLayout(1, 2));
        panBottom.add(startBtn);
        panBottom.add(stopBtn);

        add(panBottom, BorderLayout.SOUTH);
        colorizeCurrentButtons();
        setVisible(true);
    }

    private void colorizeCurrentButtons() {
        if(server.isServerWorking()){
            startBtn.setBackground(NOT_ACTIVE_COLOR);
            stopBtn.setBackground(ACTIVE_COLOR);
        }else{
            startBtn.setBackground(ACTIVE_COLOR);
            stopBtn.setBackground(NOT_ACTIVE_COLOR);
        }
    }

    @Override
    public void infoLog(String message) {
        log(message);
    }

    @Override
    public void errorLog(String message) {
        log(message);
    }

    private void log(String message){
        logField.setText(logField.getText() + message + "\n");
        server.log(message);
    }

    public Server getServer(){
        return server;
    }
}
