package ru.gb.chat.models;

public class Account {
    public static Account parseAccount(String s){
        String[] words = s.split("\\|");
        String login = words[0];
        String password = words[1];

        return new Account(login, password);
    }

    private String login;
    private String password;

    public Account(String login, String password) {
        this.login = login;
        this.password = password;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return String.format("%s|%s", login, password);
    }
}
