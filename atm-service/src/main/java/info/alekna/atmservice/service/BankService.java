package info.alekna.atmservice.service;

import info.alekna.atmservice.dto.WithdrawalResult;
import info.alekna.atmservice.exception.BankServiceException;
import io.swagger.client.ApiException;
import io.swagger.client.api.BankCoreApi;
import io.swagger.client.model.BalanceResponse;
import io.swagger.client.model.ReservationRequest;
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

    public WithdrawalResult withdrawMoney(final String cardNumber, final int amount) throws BankServiceException {

        final String accountNumber;
        try {
            var cardResponse = apiClient.getAccountNumberByCard(cardNumber);
            accountNumber = cardResponse.getAccountNumber();
        } catch (ApiException exception) {
            handleIoException(exception);
            throw new BankServiceException.AccountNotFound("Couldn't retrieve account number because card was not recognized");
        }

        if (!cashDispenser.hasBanknotesFor(amount)) {
            throw new BankServiceException.InsufficientCash("ATM can't issue banknotes for specified amount");
        }

        final int balance;
        try {
            var balanceResponse = apiClient.getAccountBalance(accountNumber);
            balance = balanceResponse.getBalance();
        } catch (ApiException exception) {
            handleIoException(exception);
            // This shouldn't happen here but just in case...
            throw new BankServiceException.AccountNotFound("Couldn't retrieve account balance because account was not found");
        }

        if (balance < amount) {
            throw new BankServiceException.BalanceTooLow("Balance in account is too low to fulfill the withdrawal");
        }

        final String reservationId;

        try {
            var reservationResponse = apiClient.reserveMoneyOnAccount(new ReservationRequest().accountNumber(accountNumber).reservedAmount(amount));
            reservationId = reservationResponse.getReservationId();
        } catch (ApiException exception) {
            handleIoException(exception);
            if (exception.getCode() == 400) {
                // There was some race condition and money were reserved somewhere else
                throw new BankServiceException.BalanceTooLow("Couldn't reserve money on account because balance is too low");
            } else if (exception.getCode() == 404) {
                // This also shouldn't happen here but just in case...
                throw new BankServiceException.AccountNotFound("Couldn't reserve money because account was not found");
            } else {
                throw new BankServiceException("Unknown server error", exception);
            }
        }

        if (cashDispenser.issueMoney(amount)) {
            try {
                apiClient.commitReservationOnAccount(reservationId);
                return new WithdrawalResult(accountNumber, amount);
            } catch (ApiException exception) {
                handleIoException(exception);
            }
        } else {
            try {
                apiClient.cancelMoneyReservationOnAccount(reservationId);
                return new WithdrawalResult(accountNumber, 0);
            } catch (ApiException exception) {
                handleIoException(exception);
                throw new BankServiceException("Couldn't cancel reservation", exception);
            }
        }
        throw new BankServiceException.InsufficientCash("Couldn't issue money");
    }
}
