package com.jsingh.moneytransfer.tests;

import com.jsingh.moneytransfer.Validate.CustomerAccountValidation;
import com.jsingh.moneytransfer.exceptions.MoneyTransferValidationExceptions;
import com.jsingh.moneytransfer.model.CustomerAccount;
import com.jsingh.moneytransfer.repository.MoneyTransferRepository;
import com.jsingh.moneytransfer.repository.MoneyTransferRepositoryImpl;
import com.jsingh.moneytransfer.service.MoneyTransferService;
import com.jsingh.moneytransfer.service.MoneyTransferServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.when;


public class MoneyTransferServiceTest {
MoneyTransferService moneyTransferService;

MoneyTransferRepository moneyTransferRepository;
CustomerAccountValidation customerAccountValidation;

    @Before
    public void setup(){
        customerAccountValidation = Mockito.mock(CustomerAccountValidation.class);
        moneyTransferRepository = Mockito.mock(MoneyTransferRepository.class);
        moneyTransferService = new MoneyTransferServiceImpl(moneyTransferRepository, customerAccountValidation);
    }

    private CustomerAccount setTestCustomerAccountData(String accountId, double amount){
        CustomerAccount customerAccount = new CustomerAccount();
        customerAccount.setCustomerAccountId(accountId);
        customerAccount.setAccountBalance(amount);
        return customerAccount;
    }
    @Test
    public void testGetAccountDetailsReturnNull(){

        when ( moneyTransferRepository.getAccountDetails("1")).thenReturn(null);
        when ( customerAccountValidation.validateAccountId("1")).thenReturn(true);
        CustomerAccount customerAccount = moneyTransferService.getAccount("1");

        assertNull(customerAccount);
    }

    @Test
    public void testGetAccountDetailsReturnData(){
        CustomerAccount customerAccount = setTestCustomerAccountData("1", 300);

        when ( moneyTransferRepository.getAccountDetails("1")).thenReturn(customerAccount);
        when ( customerAccountValidation.validateAccountId("1")).thenReturn(true);

        CustomerAccount customerAccountResponse = moneyTransferService.getAccount("1");

        assertEquals(customerAccountResponse.getCustomerAccountId(),"1");
        assertEquals(customerAccountResponse.getAccountBalance(),300,Double.MIN_VALUE);
    }

    @Test
    public void testGetAccountDetailsReturnError(){
        CustomerAccount customerAccount = setTestCustomerAccountData("", 300);
        boolean errorRaised = false;

        when ( moneyTransferRepository.getAccountDetails("")).thenReturn(customerAccount);
        when ( customerAccountValidation.validateAccountId("")).thenThrow(new MoneyTransferValidationExceptions("Validation Error"));

        try {
            CustomerAccount customerAccountResponse = moneyTransferService.getAccount("");
        }catch (MoneyTransferValidationExceptions e){
            errorRaised = true;
        }
        assertEquals(errorRaised, true);
    }

    @Test
    public void testSaveAccount(){
        CustomerAccount customerAccount = setTestCustomerAccountData("1", 300);
        boolean returnVal;
        when ( moneyTransferRepository.saveMoney("1", 300)).thenReturn(true);
        when ( customerAccountValidation.validateAccountId("1")).thenReturn(true);

        returnVal = moneyTransferService.saveMoney("1", 300);

        assertEquals(returnVal, true);
    }
    @Test
    public void testTransferMoney(){
        CustomerAccount customerAccountFrom = setTestCustomerAccountData("1", 1000);

        boolean returnVal;
        when ( moneyTransferRepository.transferMoney("1", "2", 200)).thenReturn(true);
        when ( customerAccountValidation.validateAccountId("1")).thenReturn(true);
        when ( customerAccountValidation.validateAccountId("2")).thenReturn(true);

        returnVal = moneyTransferService.transferMoney("1", "2", 200);

        assertEquals(returnVal, true);
    }
}