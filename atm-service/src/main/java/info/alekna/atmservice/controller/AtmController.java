package info.alekna.atmservice.controller;

import info.alekna.atmservice.dto.WithdrawalRequest;
import info.alekna.atmservice.dto.WithdrawalResult;
import info.alekna.atmservice.exception.BankServiceException;
import info.alekna.atmservice.service.BankService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class AtmController {

    @Autowired
    private BankService bankService;

    @Operation(summary = "Withdraw money from ATM")
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Money successfully dispensed",
                    content = {
                        @Content(
                                mediaType = "application/json",
                                schema = @Schema(implementation = WithdrawalResult.class)
                        )
                    }
            )
    })
    @PostMapping("/issue/money")
    @ResponseBody
    public WithdrawalResult withdrawMoney(@Valid @RequestBody WithdrawalRequest withdrawalRequest) throws BankServiceException {
        return bankService.withdrawMoney(withdrawalRequest.cardNumber(), withdrawalRequest.amount());
    }
}
