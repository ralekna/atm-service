package info.alekna.atmservice.service;

import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.Map;

@Service
public class CashDispenser {

    private Map<Integer, Integer> availableBanknotes = Map.of(
            100_00, 7,
            50_00, 5,
            20_00, 3,
            10_00, 2,
            5_00, 1
    );

//    public boolean hasBanknotesFor(Integer amount) {
//        final Integer remainder = availableBanknotes.entrySet().stream().sorted(Comparator.comparingInt((entry) -> entry.getKey())).reduce(amount, (memo, banknote) -> {
//            if (banknote.getValue() > 0) {
//                var minimalRemainder =  memo % banknote.getKey();
//                if (memo / banknote.getKey() * banknote.getValue()) {
//
//                }
//                return memo - (memo / ())
//            }
//            return 0;
//        }, Integer::sum);
//        return remainder == 0;
//    }

    public boolean hasBanknotesFor(Integer amount) {
        return true;
    }

    public boolean issueMoney(int amount) {
        return true;
    }

}
