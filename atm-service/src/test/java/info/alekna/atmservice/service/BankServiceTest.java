package info.alekna.atmservice.service;

import info.alekna.atmservice.dto.WithdrawalResult;
import info.alekna.atmservice.exception.BankServiceException;
import io.swagger.client.ApiException;
import io.swagger.client.api.BankCoreApi;
import io.swagger.client.model.BalanceResponse;
import io.swagger.client.model.CardResponse;
import io.swagger.client.model.ReservationRequest;
import io.swagger.client.model.ReservationResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.MockReset;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
public class BankServiceTest {

    @MockBean(reset = MockReset.AFTER)
    private BankCoreApi bankCoreApi;

    @MockBean(reset = MockReset.AFTER)
    private CashDispenser cashDispenser;

    @Autowired
    private BankService bankService;

    @Test
    @DisplayName("Should throw a BankServiceException.ServiceUnreachable exception if there are connectivity problems with Bank Core API")
    public void shouldThrowAServiceUnreachableExceptionIfCardAccountIsNotFound() throws Exception {

        String cardNumber = "4731055520998487";

        doThrow(new ApiException(new IOException())).when(bankCoreApi).getAccountNumberByCard(cardNumber);

        assertThrows(
                BankServiceException.ServiceUnreachable.class,
                () -> bankService.withdrawMoney(cardNumber, 1000)
        );
    }

    @Test
    @DisplayName("Should throw an exception if account associated with card is not found")
    public void shouldThrowAnExceptionIfCardAccountIsNotFound() throws Exception {

        String cardNumber = "4731055520998487";

        doThrow(new ApiException()).when(bankCoreApi).getAccountNumberByCard(cardNumber);

        assertThrows(
                BankServiceException.AccountNotFound.class,
                () -> bankService.withdrawMoney(cardNumber, 100_00)
        );
    }

    @Test
    @DisplayName("Should throw an exception if ATM doesn't have enough cash")
    public void shouldThrowAnExceptionIfAtmDoesNotHaveEnoughCash() throws Exception {

        String cardNumber = "4731055520998487";
        String accountNumber = "LT355636149269234955";
        int amount = 100_00;

        doReturn(false).when(cashDispenser).hasBanknotesFor(amount);
        doReturn(new CardResponse().accountNumber(accountNumber)).when(bankCoreApi).getAccountNumberByCard(cardNumber);

        assertThrows(
                BankServiceException.InsufficientCash.class,
                () -> bankService.withdrawMoney(cardNumber, amount)
        );
    }

    @Test
    @DisplayName("Should throw an exception if account balance is too low")
    public void shouldThrowAnExceptionIfAccountBalanceIsTooLow() throws Exception {

        String cardNumber = "4731055520998487";
        String accountNumber = "LT355636149269234955";
        int amount = 100_00;
        int balance = 50_00;

        doReturn(new CardResponse().accountNumber(accountNumber)).when(bankCoreApi).getAccountNumberByCard(cardNumber);
        doReturn(true).when(cashDispenser).hasBanknotesFor(amount);
        doReturn(new BalanceResponse().balance(balance)).when(bankCoreApi).getAccountBalance(accountNumber);
        assertThrows(
                BankServiceException.BalanceTooLow.class,
                () -> bankService.withdrawMoney(cardNumber, amount)
        );
    }

    @Test
    @DisplayName("Should throw an exception if can't somehow reserve amount in account")
    public void shouldThrowAnExceptionIfCanNotReserveMoney() throws Exception {

        String cardNumber = "4731055520998487";
        String accountNumber = "LT355636149269234955";
        int amount = 100_00;
        int balance = 200_00;

        doReturn(new CardResponse().accountNumber(accountNumber)).when(bankCoreApi).getAccountNumberByCard(cardNumber);
        doReturn(true).when(cashDispenser).hasBanknotesFor(amount);
        doReturn(new BalanceResponse().balance(balance)).when(bankCoreApi).getAccountBalance(accountNumber);
        doThrow(new ApiException(400, "Can't reserve money")).when(bankCoreApi).reserveMoneyOnAccount(new ReservationRequest(accountNumber, amount));

        assertThrows(
                BankServiceException.BalanceTooLow.class,
                () -> bankService.withdrawMoney(cardNumber, amount)
        );
    }

    @Test
    @DisplayName("Should issue money successfully")
    public void shouldSuccessfullyIssueMoney() throws Exception {

        String cardNumber = "4731055520998487";
        String accountNumber = "LT355636149269234955";
        int amount = 100_00;
        int balance = 200_00;
        String reservationId = "123";

        doReturn(true).when(cashDispenser).hasBanknotesFor(amount);
        doReturn(true).when(cashDispenser).issueMoney(amount);
        doReturn(new CardResponse().accountNumber(accountNumber)).when(bankCoreApi).getAccountNumberByCard(cardNumber);
        doReturn(new BalanceResponse().balance(balance)).when(bankCoreApi).getAccountBalance(accountNumber);
        doReturn(new ReservationResponse().reservationId(reservationId)).when(bankCoreApi).reserveMoneyOnAccount(new ReservationRequest(accountNumber, amount));

        assertEquals(new WithdrawalResult(accountNumber, amount), bankService.withdrawMoney(cardNumber, amount));
    }
}
