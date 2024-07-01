package hcs.hellospring.payment;

import java.math.BigDecimal;

public interface ExRateProvider {
    BigDecimal getExRate(String currency);
}
