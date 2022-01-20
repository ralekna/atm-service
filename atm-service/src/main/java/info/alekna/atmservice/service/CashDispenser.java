package info.alekna.atmservice.service;

import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.Comparator;
import java.util.Map;

@Service
public class CashDispenser {

    private Map<Integer, Integer> availableBanknotes;

    public CashDispenser() {
        // It has some hardcoded cash
        availableBanknotes = Collections.synchronizedMap(Map.of(
                100_00, 7,
                50_00, 5,
                20_00, 3,
                10_00, 2,
                5_00, 1
        ));
    }

    public CashDispenser(Map<Integer, Integer> availableBanknotes) {
        this.availableBanknotes = Collections.synchronizedMap(availableBanknotes);
    }

    public Map<Integer, Integer> getAvailableBanknotes() {
        return availableBanknotes;
    }

    public void setAvailableBanknotes(Map<Integer, Integer> availableBanknotes) {
        this.availableBanknotes = Collections.synchronizedMap(availableBanknotes);
    }

    synchronized public boolean hasBanknotesFor(Integer amount) {
        if (amount <= 0) {
            return false;
        }
        final Integer remainder = availableBanknotes
                .entrySet()
                .stream()
                .sorted(
                        Comparator
                                .comparingInt((Map.Entry<Integer, Integer> entry) -> entry.getKey())
                                .reversed()
                )
                .reduce(
                        amount,
                        (memo, banknote) -> {
                            var numberOfBanknotes = banknote.getValue();
                            var banknoteValue = banknote.getKey();
                            return memo - Math.min(
                                    Math.floorDiv(memo, banknoteValue), numberOfBanknotes
                            ) * banknoteValue;
                        }, Integer::sum);
        return remainder == 0;
    }

    synchronized public boolean issueMoney(int amount) {
        return hasBanknotesFor(amount);
        // TODO: implement deduction from available banknotes
    }
}
