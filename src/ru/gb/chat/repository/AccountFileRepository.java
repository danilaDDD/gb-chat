package ru.gb.chat.repository;

import ru.gb.chat.exceptions.ServerException;
import ru.gb.chat.models.Account;

import java.util.List;
import java.util.stream.Collectors;

public class AccountFileRepository implements AccountRepository{
    private final FileRepository fileRepository = new FileRepository("accounts.txt");

    @Override
    public Account find(String login, String password) throws ServerException {
        return getAll().stream()
                .filter(account -> account.getLogin().equals(login) && account.getPassword().equals(password))
                .findFirst().get();
    }

    private List<Account> getAll() throws ServerException {
        return fileRepository.getAllLines()
                .stream().map(Account::parseAccount).collect(Collectors.toList());
    }

    @Override
    public void save(Account account) throws ServerException {
        fileRepository.saveString(account + "\n");
    }
}
