package com.jsingh.moneytransfer.service;


import com.jsingh.moneytransfer.Validate.CustomerAccountValidation;
import com.jsingh.moneytransfer.model.CustomerAccount;
import com.jsingh.moneytransfer.repository.MoneyTransferRepository;


public class MoneyTransferServiceImpl implements MoneyTransferService{

    private MoneyTransferRepository moneyTransferRepository;
    private CustomerAccountValidation customerAccountValidation;

    public MoneyTransferServiceImpl(MoneyTransferRepository moneyTransferRepository, CustomerAccountValidation customerAccountValidation) {
        this.moneyTransferRepository = moneyTransferRepository;
        this.customerAccountValidation = customerAccountValidation;
    }

    public boolean transferMoney (String fromAccountId, String toAccountId, double amount) {
        customerAccountValidation.validateAccountId(fromAccountId);
        customerAccountValidation.validateAccountId(toAccountId);
        CustomerAccount customerAccount = getAccount(fromAccountId);
        customerAccountValidation.validateCustomerAccount(customerAccount);
        synchronized (this) {
            customerAccountValidation.validateTransferAmount(customerAccount, amount);
            return moneyTransferRepository.transferMoney(fromAccountId, toAccountId, amount);
        }
    }

    public boolean saveMoney(String accountId, double amount) {
        customerAccountValidation.validateAccountId(accountId);
        customerAccountValidation.validateAmount(amount);
        return moneyTransferRepository.saveMoney(accountId, amount);
    }

    public CustomerAccount getAccount(String accountId) {
        customerAccountValidation.validateAccountId(accountId);
        return moneyTransferRepository.getAccountDetails(accountId);
    }
}
