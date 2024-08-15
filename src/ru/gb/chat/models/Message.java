package ru.gb.chat.models;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

public class Message {
    private static final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");

    public static Message parseMessage(String s){
        String[] fieldsArray = s.split("\\|");
        String login, password, ipAddress, port, message;
        LocalDateTime created;

        login = fieldsArray[0];
        password = fieldsArray[1];

        ipAddress = fieldsArray[2];
        port = fieldsArray[3];
        message = fieldsArray[4];
        created = LocalDateTime.from(dateTimeFormatter.parse(fieldsArray[5]));

        return new Message(new Account(login, password),
                   ipAddress, port, message, created);

    }

    private final String port;
    private final String ipAddress;
    private final LocalDateTime created;
    private final String message;
    private Account account;

    public Message(Account account, String port, String ipAddress, String message, LocalDateTime created) {
        this.account = account;
        this.ipAddress = ipAddress;
        this.message = message;
        this.port = port;
        this.created = LocalDateTime.now();
    }

    public Message(Account account, String ipAddress, String port, String message) {
       this(account, ipAddress, port, message, LocalDateTime.now());
    }

    public Message(String ipAddress, String message, String port) {
        this(null, port, ipAddress, message);
    }

    public String getPort() {
        return port;
    }

    public String getMessage() {
        return message;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public String toStringMessage() {
        return String.format("%s:%s:%s", account.getLogin(), message, created);
    }

    @Override
    public String toString() {
        return String.join("|", List.of(account.getLogin(), account.getPassword(), ipAddress, port, message, dateTimeFormatter.format(created)));
    }
}
