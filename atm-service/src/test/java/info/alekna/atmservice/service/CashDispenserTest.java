package info.alekna.atmservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class CashDispenserTest {

    @Test
    public void shouldReturnTrueIfCashDispenserCanIssueSpecifiedAmount() {
        CashDispenser cashDispenser = new CashDispenser(
                Map.of(
                        100_00, 7,
                        50_00, 5,
                        20_00, 3,
                        10_00, 2,
                        5_00, 1
                )
        );

        assertTrue(cashDispenser.hasBanknotesFor(85_00));
        assertTrue(cashDispenser.hasBanknotesFor(75_00));
        assertTrue(cashDispenser.hasBanknotesFor(945_00));
        assertFalse(cashDispenser.hasBanknotesFor(0));
        assertFalse(cashDispenser.hasBanknotesFor(83_00));
        assertFalse(cashDispenser.hasBanknotesFor(2000_00));
        assertFalse(cashDispenser.hasBanknotesFor(-100_00));

    }

}
