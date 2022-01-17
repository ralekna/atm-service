/*
 * OpenAPI definition
 * No description provided (generated by Swagger Codegen https://github.com/swagger-api/swagger-codegen)
 *
 * OpenAPI spec version: 1.0.0-oas3
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */

package io.swagger.client.api;

import io.swagger.client.ApiException;
import io.swagger.client.model.BalanceResponse;
import io.swagger.client.model.CardResponse;
import io.swagger.client.model.ReservationRequest;
import io.swagger.client.model.ReservationResponse;
import org.junit.Test;
import org.junit.Ignore;

/**
 * API tests for BankCoreApi
 */
@Ignore
public class BankCoreApiTest {

    private final BankCoreApi api = new BankCoreApi();

    /**
     * 
     *
     * Commit withdrawal
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void commitReservationOnAccountTest() throws ApiException {
        String reservationId = null;
        String response = api.commitReservationOnAccount(reservationId);

        // TODO: test validations
    }
    /**
     * 
     *
     * Get Bank Account balance
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getAccountBalanceTest() throws ApiException {
        String accountNumber = null;
        BalanceResponse response = api.getAccountBalance(accountNumber);

        // TODO: test validations
    }
    /**
     * 
     *
     * Get Bank Account number by Credit Card number
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void getAccountNumberByCardTest() throws ApiException {
        String cardNumber = null;
        CardResponse response = api.getAccountNumberByCard(cardNumber);

        // TODO: test validations
    }
    /**
     * 
     *
     * Reserve money on a Bank Account
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void reserveMoneyOnAccountTest() throws ApiException {
        ReservationRequest body = null;
        ReservationResponse response = api.reserveMoneyOnAccount(body);

        // TODO: test validations
    }
    /**
     * 
     *
     * Cancel reservation
     *
     * @throws ApiException
     *          if the Api call fails
     */
    @Test
    public void cancelMoneyReservationOnAccountTest() throws ApiException {
        String reservationId = null;
        String response = api.cancelMoneyReservationOnAccount(reservationId);

        // TODO: test validations
    }
}