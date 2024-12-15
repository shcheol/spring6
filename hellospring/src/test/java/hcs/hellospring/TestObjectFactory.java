package hcs.hellospring;

import hcs.hellospring.exrate.CachedExRateProvider;
import hcs.hellospring.exrate.WebApiExRateProvider;
import hcs.hellospring.payment.ExRateProvider;
import hcs.hellospring.payment.ExRateProviderStub;
import hcs.hellospring.payment.PaymentService;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;

@TestConfiguration
public class TestObjectFactory {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(exRateProvider(), clock());
    }

    @Bean
    public ExRateProvider exRateProvider() {
        return new ExRateProviderStub(BigDecimal.valueOf(1_000));
    }

    @Bean
    public Clock clock(){
        return Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }
}
