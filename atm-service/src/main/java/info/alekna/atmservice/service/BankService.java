package info.alekna.atmservice.service;

import info.alekna.atmservice.exception.BankServiceException;
import io.swagger.client.ApiException;
import io.swagger.client.api.BankCoreApi;
import io.swagger.client.model.BalanceResponse;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class BankService {

    @Autowired
    private BankCoreApi apiClient;

    @Autowired
    private CashDispenser cashDispenser;

    private static void handleIoException(final @NotNull ApiException exception) throws BankServiceException {
        if (exception.getCause() instanceof IOException) {
            throw new BankServiceException.ServiceUnreachable("Gateway error");
        }
    }

    public void withdrawMoney(final String cardNumber, final int amount) throws BankServiceException {

        final String accountNumber;
        try {
            var cardResponse = apiClient.getAccountNumberByCard(cardNumber);
            accountNumber = cardResponse.getAccountNumber();
        } catch (ApiException exception) {
            handleIoException(exception);
            throw new BankServiceException.AccountNotFound("Couldn't retrieve account balance because account was not found");
        }

        if (!cashDispenser.hasBanknotesFor(amount)) {
            throw new BankServiceException.InsuficientBanknotes("ATM can't issue banknotes for specified amount");
        }

        final BalanceResponse balanceResponse;
        try {
            balanceResponse = apiClient.getAccountBalance(accountNumber);
        } catch (ApiException exception) {
            handleIoException(exception);
            throw new BankServiceException.AccountNotFound("Couldn't retrieve account balance because account was not found");
        }

        if (balanceResponse.getBalance() < amount) {
            throw new BankServiceException.BalanceTooLow("Balance in account is too low to fulfill the withdrawal");
        }
    }
}
