package com.jsingh.moneytransfer.tests;

import com.jsingh.moneytransfer.model.CustomerAccount;
import com.jsingh.moneytransfer.repository.MoneyTransferRepository;
import com.jsingh.moneytransfer.repository.MoneyTransferRepositoryImpl;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MoneyTransferRepositoryTest {

    private MoneyTransferRepository moneyTransferRepository;

    @Before
    public void setup(){
        moneyTransferRepository = new MoneyTransferRepositoryImpl();
    }
    @Test
    public void testGetAccountDetailsReturnNull(){
        CustomerAccount customerAccount = moneyTransferRepository.getAccountDetails("1");
        assertEquals("Expected to return null object",customerAccount,null);
    }
    @Test
    public void testSaveAndGetMoney(){
        moneyTransferRepository.saveMoney("1", 100);
        CustomerAccount customerAccount = moneyTransferRepository.getAccountDetails("1");
        assertEquals("Customer account id should be 1",customerAccount.getCustomerAccountId(),"1");
        assertEquals("Customer account balance should be 100",customerAccount.getAccountBalance(),100.00,Double.MIN_VALUE);
    }
    @Test
    public void testTransferMoney(){
        moneyTransferRepository.saveMoney("1", 1000);
        moneyTransferRepository.transferMoney("1", "2",300);
        CustomerAccount customerFromAccount = moneyTransferRepository.getAccountDetails("1");
        CustomerAccount customerToAccount = moneyTransferRepository.getAccountDetails("2");
        assertEquals("Customer account id should be 1",customerFromAccount.getCustomerAccountId(),"1");
        assertEquals("Customer account balance should be 700",customerFromAccount.getAccountBalance(),700.00,Double.MIN_VALUE);
        assertEquals("Customer account id should be 2",customerToAccount.getCustomerAccountId(),"2");
        assertEquals("Customer account balance should be 300",customerToAccount.getAccountBalance(),300.00,Double.MIN_VALUE);
    }
}
