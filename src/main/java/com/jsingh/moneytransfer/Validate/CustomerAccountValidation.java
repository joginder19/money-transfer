package com.jsingh.moneytransfer.Validate;

import com.jsingh.moneytransfer.model.CustomerAccount;

public interface CustomerAccountValidation {
    public boolean validateAccountId(String accountId);
    public boolean validateCustomerAccount(CustomerAccount fromAccount);
    public boolean validateAmount(double amount);
    public boolean validateTransferAmount(CustomerAccount fromAccount, double amount);
}
