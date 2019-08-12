package com.jsingh.moneytransfer.repository;

import com.jsingh.moneytransfer.model.CustomerAccount;

public interface MoneyTransferRepository {
    public boolean transferMoney(String fromAccountId, String toAccountId, double amount);
    public boolean saveMoney(String accountId, double amount);
    public CustomerAccount getAccountDetails(String accountId);
}
