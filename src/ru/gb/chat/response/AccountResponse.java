package ru.gb.chat.response;

import ru.gb.chat.models.Account;
import ru.gb.chat.server.ServerStatus;

public class AccountResponse extends ServerResponse{
    Account account;

    public AccountResponse(ServerStatus status, Account account) {
        super(status);
        this.account = account;
    }

    public Account getAccount() {
        return account;
    }
}
