package com.jsingh.moneytransfer.tests;


import com.jsingh.moneytransfer.Validate.CustomerAccountValidation;
import com.jsingh.moneytransfer.Validate.CustomerAccountValidationImpl;
import com.jsingh.moneytransfer.controller.MoneyTransferController;
import com.jsingh.moneytransfer.exceptions.MoneyTransferValidationExceptions;
import com.jsingh.moneytransfer.repository.MoneyTransferRepository;
import com.jsingh.moneytransfer.repository.MoneyTransferRepositoryImpl;
import com.jsingh.moneytransfer.service.MoneyTransferService;
import com.jsingh.moneytransfer.service.MoneyTransferServiceImpl;
import com.jsingh.moneytransfer.utils.JsonUtil;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import spark.utils.IOUtils;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import static junit.framework.TestCase.fail;
import static org.junit.Assert.assertEquals;

public class MoneyTransferControllerTest {


    private CustomerAccountValidation moneyTransferValidation;
    private MoneyTransferRepository moneyTransferRepository;
    private MoneyTransferService moneyTransferService;
    private MoneyTransferController moneyTransferController;
    private JsonUtil jsonUtil;

    @Before
    public void setup() {
        moneyTransferValidation = new CustomerAccountValidationImpl();
        moneyTransferRepository = new MoneyTransferRepositoryImpl();
        moneyTransferService = new MoneyTransferServiceImpl(moneyTransferRepository, moneyTransferValidation);
        jsonUtil = new JsonUtil();
        moneyTransferController = new MoneyTransferController(moneyTransferService, jsonUtil);
    }

    private TestResponse request(String method, String path) {
        try {
            URL url = new URL("http://localhost:4567" + path);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod(method);
            connection.setDoOutput(true);
            connection.connect();
            String body = IOUtils.toString(connection.getInputStream());
            return new TestResponse(connection.getResponseCode(), body);
        } catch (IOException e) {
            e.printStackTrace();
            fail("Sending request failed: " + e.getMessage());
            return null;
        }
    }

    private static class TestResponse {

        public final String body;
        public final int status;

        public TestResponse(int status, String body) {
            this.status = status;
            this.body = body;
        }
    }

    @Test
    public void testControllerGetSuccess() {
        TestResponse res = request("GET", "/get-account?account=1");
        assertEquals(200, res.status);
    }
}
