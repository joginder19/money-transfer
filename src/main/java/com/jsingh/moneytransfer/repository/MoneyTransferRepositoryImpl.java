package com.jsingh.moneytransfer.repository;
import com.jsingh.moneytransfer.model.CustomerAccount;
import java.util.HashMap;
import java.util.Map;

public class MoneyTransferRepositoryImpl implements MoneyTransferRepository{
    Map<String, CustomerAccount> customerAccountMap = new HashMap<>();

    public boolean transferMoney(String fromAccountId, String toAccountId, double amount) {
        saveCustomerMoney(fromAccountId, -amount);
        saveCustomerMoney(toAccountId, amount);
        return true;
    }
    public boolean saveMoney(String accountId, double amount) {
        saveCustomerMoney(accountId, amount);
        return true;
    }
    public CustomerAccount getAccountDetails(String accountId) {
        return customerAccountMap.get(accountId);
    }


    private void saveCustomerMoney(String accountId, double amount) {

        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setCustomerAccountId(accountId);
        if (customerAccountMap.containsKey(accountId)) {
            customerAccount.setAccountBalance(customerAccountMap.get(accountId).getAccountBalance() + amount);
        }
        else {
            customerAccount.setAccountBalance(amount);
        }

        customerAccountMap.put(accountId, customerAccount);

    }
}
