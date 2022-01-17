package info.alekna.atmservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import info.alekna.atmservice.dto.WithdrawalRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.util.MimeTypeUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(AtmController.class)
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

    @Test
    public void shouldReturnWithdrawnAmountOnSuccess() throws Exception {
        request.content(mapper.writeValueAsString(new WithdrawalRequest("4731051429700223", 1000)));
        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(content().json(
                        """
                                {
                                    "amount": 1000,
                                    "cardNumber": "4731051429700223"
                                }
                                """, true
                ));
    }
}
