package ru.gb.chat.client;

import java.time.LocalDateTime;
import java.util.Objects;

public class ClientData {
    public static ClientData parseClientData(String s){
        String[] parts = s.split("\\|");
        LocalDateTime created = LocalDateTime.parse(parts[0]);
        int clientId = Integer.parseInt(parts[1]);
        String ipAddress = parts[2];
        String port = parts[3];
        String login = parts[4];
        String password = parts[5];
        String message = parts.length == 7? parts[6]: "";

        return new ClientData(clientId, ipAddress, port, login, password, message, created);
    }

    private final int clientId;
    private final String ipAddress;
    private final String port;
    private final String login;
    private final String password;
    private String message;
    private final LocalDateTime created;

    public ClientData(int clientId, String ipAddress, String port, String login, String password, String message, LocalDateTime created) {
        this.clientId = clientId;
        this.ipAddress = ipAddress;
        this.port = port;
        this.login = login;
        this.password = password;
        this.message = message;
        this.created = created;
    }

    public ClientData(int clientId, String ipAddress, String port, String login, String password, String message) {
        this(clientId, ipAddress, port, login, password, message, LocalDateTime.now());
    }

    public ClientData(int clientId, String ipAddress, String port, String login, String password) {
        this(clientId, ipAddress, port, login, password, "");
    }

    public int getClientId() {
        return clientId;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public String getLogin() {
        return login;
    }

    public String getMessage() {
        return message;
    }

    public String getPassword() {
        return password;
    }

    public String getPort() {
        return port;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ClientData that = (ClientData) o;
        return clientId == that.clientId && Objects.equals(created, that.created);
    }

    @Override
    public int hashCode() {
        return Objects.hash(clientId, created);
    }

    @Override
    public String toString() {
        return String.format("%s|%s|%s|%s|%s|%s|%s", created, clientId, ipAddress, port, login, password, message);
    }
}
