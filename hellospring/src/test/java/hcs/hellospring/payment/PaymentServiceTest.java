package hcs.hellospring.payment;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

import static java.math.BigDecimal.*;
import static org.assertj.core.api.Assertions.*;

class PaymentServiceTest {
    Clock clock;

    @BeforeEach
    void beforeEach(){
        this.clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
    }

    @Test
    void convertedAmount() throws IOException {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

        testAmount(valueOf(500), valueOf(5_000), clock);
        testAmount(valueOf(1000), valueOf(10_000), clock);
        testAmount(valueOf(2000), valueOf(20_000), clock);

        // 원화환삼금액의 유효시간 계산
//        assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
//        assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
    }

    @Test
    void validUntil() throws IOException {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(valueOf(1000)), clock);

        Payment payment = paymentService.prepare(10L, "USD", TEN);

        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime expectedValidUntil = now.plusMinutes(30);
        Assertions.assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);

    }
    private static void testAmount(BigDecimal exRate, BigDecimal convertedAmount, Clock clock) throws IOException {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate), clock);

        Payment payment = paymentService.prepare(10L, "USD", TEN);

        assertThat(payment.getExRate()).isEqualByComparingTo(exRate);
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(convertedAmount);

    }

}