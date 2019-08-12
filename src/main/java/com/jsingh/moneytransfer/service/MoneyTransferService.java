package com.jsingh.moneytransfer.service;

import com.jsingh.moneytransfer.model.CustomerAccount;

public interface MoneyTransferService {
    public boolean transferMoney(String fromAccountId, String toAccountId, double amount);
    public boolean saveMoney(String accountId, double amount) ;
    public CustomerAccount getAccount(String accountId) ;
}
