package info.alekna.atmservice.service;

import info.alekna.atmservice.exception.BankServiceException;
import io.swagger.client.ApiException;
import io.swagger.client.api.BankCoreApi;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.doThrow;

@SpringBootTest
public class BankServiceTest {

    @MockBean
    private BankCoreApi bankCoreApi;

    @Autowired
    private BankService bankService;

    @Test
    @DisplayName("Should throw an exception if account associated with card is not found")
    public void shouldThrowAnExceptionIfCardAccountIsNotFound() throws Exception {

        String cardNumber = "4731055520998487";

        doThrow(new ApiException()).when(bankCoreApi).getAccountNumberByCard(cardNumber);

        Exception exception = assertThrows(
                BankServiceException.AccountNotFound.class,
                () -> bankService.withdrawMoney(cardNumber, 1000)
        );
    }
}
