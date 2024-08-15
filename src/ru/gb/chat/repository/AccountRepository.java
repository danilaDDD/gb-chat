package ru.gb.chat.repository;

import ru.gb.chat.exceptions.ServerException;
import ru.gb.chat.models.Account;

public interface AccountRepository {
    Account find(String login, String password) throws ServerException;

    void save(Account account) throws ServerException;
}
