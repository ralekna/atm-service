package info.alekna.atmservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.alekna.atmservice.dto.WithdrawalRequest;
import info.alekna.atmservice.dto.WithdrawalResult;
import io.swagger.client.model.BalanceResponse;
import io.swagger.client.model.CardResponse;
import io.swagger.client.model.ReservationResponse;
import org.junit.Ignore;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.wiremock.AutoConfigureWireMock;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.MimeTypeUtils;
import com.github.tomakehurst.wiremock.client.WireMock;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureWireMock(port = 8088)
@TestPropertySource(properties = {"app.client.bank-core.base-url=http://localhost:8088"})
@AutoConfigureMockMvc
public class AtmControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper mapper;

    private MockHttpServletRequestBuilder request;

    @BeforeEach
    public void setUp() {

        request = post("/issue/money")
            .accept(MimeTypeUtils.APPLICATION_JSON_VALUE)
            .contentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
    }

    @AfterEach
    public void afterEach() {
        WireMock.reset();
    }

    @Test
    public void shouldReturn400InvalidCardNumberFormat() throws Exception {

        request.content(mapper.writeValueAsString(new WithdrawalRequest("47310514", 100_00)));
        mockMvc.perform(request)
                .andExpect( status().isBadRequest());
    }

    @Test
    public void shouldReturn400NegativeAmount() throws Exception {

        request.content(mapper.writeValueAsString(new WithdrawalRequest("4731051429700223", -100_00)));
        mockMvc.perform(request)
                .andExpect(status().isBadRequest());
    }

    @Test
    public void shouldReturn404CardNotRecognized() throws Exception {

        WireMock.stubFor(
                WireMock.get(WireMock.urlMatching("/v1/account/([0-9\\s-]+)"))
                        .willReturn(WireMock.aResponse().withStatus(404))
        );

        request.content(mapper.writeValueAsString(new WithdrawalRequest("4731051429700223", 100_00)));
        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    public void shouldReturn503AtmDoesNotHaveEnoughCash() throws Exception {

        String cardNumber = "4731-0555-2099-8487";
        String accountNumber = "LT355636149269234955";
        int amount = 10000_00;

        WireMock.stubFor(
                WireMock.get(WireMock.urlMatching("/v1/account/([0-9\\s-]+)"))
                        .willReturn(
                                WireMock.aResponse()
                                        .withStatus(200)
                                        .withBody(
                                                mapper.writeValueAsString(new CardResponse().accountNumber(accountNumber))
                                        )
                        )
        );

        request.content(mapper.writeValueAsString(new WithdrawalRequest(cardNumber, amount)));
        mockMvc.perform(request)
                .andExpect(status().isServiceUnavailable());
    }

    @Test
    public void shouldReturn403BalanceTooLow() throws Exception {

        String cardNumber = "4731-0555-2099-8487";
        String accountNumber = "LT355636149269234955";
        int amount = 100_00;
        int availableBalance = 50_00;

        WireMock.stubFor(
                WireMock.get(WireMock.urlMatching("/v1/account/([0-9\\s-]+)"))
                        .willReturn(
                                WireMock.aResponse()
                                        .withStatus(200)
                                        .withBody(
                                                mapper.writeValueAsString(new CardResponse().accountNumber(accountNumber))
                                        )
                        )
        );

        WireMock.stubFor(
                WireMock.get(WireMock.urlMatching("/v1/account/balance/(.+$)"))
                        .willReturn(
                                WireMock.aResponse()
                                        .withStatus(200)
                                        .withBody(
                                                mapper.writeValueAsString(new BalanceResponse().balance(availableBalance))
                                        )
                        )
        );

        request.content(mapper.writeValueAsString(new WithdrawalRequest(cardNumber, amount)));
        mockMvc.perform(request)
                .andExpect(status().isForbidden());
    }

    @Test
    public void shouldReturn200AndIssueMoney() throws Exception {

        String cardNumber = "4731-0555-2099-8487";
        String accountNumber = "LT355636149269234955";
        int amount = 100_00;
        int availableBalance = 200_00;
        String reservationId = "123";

        WireMock.stubFor(
                WireMock.get(WireMock.urlMatching("/v1/account/([0-9\\s-]+)"))
                        .willReturn(
                                WireMock.aResponse()
                                        .withStatus(200)
                                        .withBody(
                                                mapper.writeValueAsString(new CardResponse().accountNumber(accountNumber))
                                        )
                        )
        );

        WireMock.stubFor(
                WireMock.get(WireMock.urlMatching("/v1/account/balance/(.+$)"))
                        .willReturn(
                                WireMock.aResponse()
                                        .withStatus(200)
                                        .withBody(
                                                mapper.writeValueAsString(new BalanceResponse().balance(availableBalance))
                                        )
                        )
        );

        WireMock.stubFor(
                WireMock.post(WireMock.urlMatching("/v1/reservation"))
                        .willReturn(
                                WireMock.aResponse()
                                        .withStatus(200)
                                        .withBody(
                                                mapper.writeValueAsString(new ReservationResponse().reservationId(reservationId))
                                        )
                        )
        );

        WireMock.stubFor(
                WireMock.post(WireMock.urlMatching("/v1/reservation/commit/(.*$)"))
                        .willReturn(
                                WireMock.aResponse()
                                        .withStatus(204)
                                        .withBody(
                                                ""
                                        )
                        )
        );

        request.content(mapper.writeValueAsString(new WithdrawalRequest(cardNumber, amount)));
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(mapper.writeValueAsString(new WithdrawalResult(accountNumber, amount))));
    }

}
