package com.jsingh.moneytransfer;

import com.jsingh.moneytransfer.Validate.CustomerAccountValidationImpl;
import com.jsingh.moneytransfer.controller.MoneyTransferController;
import com.jsingh.moneytransfer.repository.MoneyTransferRepositoryImpl;
import com.jsingh.moneytransfer.service.MoneyTransferService;
import com.jsingh.moneytransfer.service.MoneyTransferServiceImpl;
import com.jsingh.moneytransfer.utils.JsonUtil;


public class MoneyTransferApplication {

	private static MoneyTransferService moneyTransferService;
	private static JsonUtil jsonUtil;

	public static void main(String[] args) {
		moneyTransferService = new MoneyTransferServiceImpl(new MoneyTransferRepositoryImpl(),
				                                            new CustomerAccountValidationImpl());
		jsonUtil = new JsonUtil();
		new MoneyTransferController(moneyTransferService, jsonUtil);
	}
}
