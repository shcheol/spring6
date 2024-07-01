package hcs.hellospring.exrate;

import hcs.hellospring.payment.ExRateProvider;

import java.math.BigDecimal;

public class SimpleExRateProvider implements ExRateProvider {

    @Override
    public BigDecimal getExRate(String currency) {
        if (currency.equals("USD")) return new BigDecimal(1000);
        throw new IllegalArgumentException("지원하지 않는 통화입니다.");
    }
}