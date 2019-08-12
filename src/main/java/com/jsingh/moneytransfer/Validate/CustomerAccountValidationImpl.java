package com.jsingh.moneytransfer.Validate;

import com.jsingh.moneytransfer.exceptions.MoneyTransferValidationExceptions;
import com.jsingh.moneytransfer.model.CustomerAccount;

public class CustomerAccountValidationImpl implements CustomerAccountValidation {

    @Override
    public boolean validateAccountId(String accountId){
        if (accountId == null ||accountId.isEmpty()){
            throw new MoneyTransferValidationExceptions ("Customer account is null");
        }
        return true;
        //check customer account exists
    }
    @Override
    public boolean validateAmount(double amount){
        if (amount <0){
            throw new MoneyTransferValidationExceptions ("Transfer amount can not be negative");
        }
        return true;
    }
    public boolean validateCustomerAccount(CustomerAccount fromAccount){
        if (fromAccount == null){
            throw new MoneyTransferValidationExceptions ("Customer  account " + fromAccount + "  does not exists, use save-money to create balance");
        }
        return true;
        //check customer account exists
    }
    public boolean validateTransferAmount( CustomerAccount fromAccount, double amount) {
        if (fromAccount.getAccountBalance() - amount < 0){
            throw new MoneyTransferValidationExceptions ("Customer  account " + fromAccount + " does not have sufficient balance");
        }
        return true;
    }
}
