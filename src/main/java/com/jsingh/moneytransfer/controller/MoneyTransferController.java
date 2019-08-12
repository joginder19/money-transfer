package com.jsingh.moneytransfer.controller;

import com.jsingh.moneytransfer.exceptions.MoneyTransferValidationExceptions;
import com.jsingh.moneytransfer.service.MoneyTransferService;
import com.jsingh.moneytransfer.utils.JsonUtil;

import static spark.Spark.*;

public class MoneyTransferController {
    private MoneyTransferService moneyTransferService;
    private JsonUtil jsonUtil;

    public MoneyTransferController(final MoneyTransferService moneyTransferService, JsonUtil jsonUtil) {
        this.moneyTransferService = moneyTransferService;
        this.jsonUtil = jsonUtil;

        after((req, res) -> {
            res.type("application/json");
        });

        get("/get-account", (req, res) ->
                        moneyTransferService.getAccount(req.queryParams("account").toString())
                , jsonUtil.json());

        post("/transfer-money", (req, res) ->
                        moneyTransferService.transferMoney(req.queryParams("from_account").toString()
                                , req.queryParams("to_account").toString()
                                , Double.parseDouble(req.queryParams("amount")))
                , jsonUtil.json());

        put("/save-money", (req, res) ->
                        moneyTransferService.saveMoney(req.queryParams("account").toString(),
                                Double.parseDouble(req.queryParams("amount")))
                , jsonUtil.json());

        exception(RuntimeException.class, (e, req, res) -> {
            res.status(400);
            res.body(jsonUtil.toJson(new MoneyTransferValidationExceptions(e.getMessage())));
        });
    }
}