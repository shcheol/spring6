package hcs.hellospring.payment;

import hcs.hellospring.TestObjectFactory;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.Clock;
import java.time.LocalDateTime;

import static java.math.BigDecimal.TEN;
import static java.math.BigDecimal.valueOf;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@ContextConfiguration(classes = TestObjectFactory.class)
class PaymentServiceSpringTest {

//    @Autowired BeanFactory beanFactory;
    @Autowired PaymentService paymentService;
    @Autowired ExRateProviderStub exRateProviderStub;

    @Autowired Clock clock;

    @Test
    void convertedAmount() throws IOException {

//        BeanFactory beanFactory = new AnnotationConfigApplicationContext(TestObjectFactory.class);
//        PaymentService paymentService = beanFactory.getBean(PaymentService.class);

        Payment payment = paymentService.prepare(100L, "USD", TEN);

        assertThat(payment.getExRate()).isEqualByComparingTo(valueOf(1000));
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(valueOf(10_000));

        exRateProviderStub.setExRate(valueOf(500));
        Payment payment2 = paymentService.prepare(100L, "USD", TEN);

        assertThat(payment2.getExRate()).isEqualByComparingTo(valueOf(500));
        assertThat(payment2.getConvertedAmount()).isEqualByComparingTo(valueOf(5_000));


        // 원화환삼금액의 유효시간 계산
//        assertThat(payment.getValidUntil()).isAfter(LocalDateTime.now());
//        assertThat(payment.getValidUntil()).isBefore(LocalDateTime.now().plusMinutes(30));
    }

    @Test
    void validUntil() throws IOException {
        Payment payment = paymentService.prepare(10L, "USD", TEN);

        LocalDateTime now = LocalDateTime.now(this.clock);
        LocalDateTime expectedValidUntil = now.plusMinutes(30);
        Assertions.assertThat(payment.getValidUntil()).isEqualTo(expectedValidUntil);

    }

    private static void testAmount(BigDecimal exRate, BigDecimal convertedAmount) throws IOException {
        PaymentService paymentService = new PaymentService(new ExRateProviderStub(exRate), Clock.systemDefaultZone());

        Payment payment = paymentService.prepare(10L, "USD", TEN);

        assertThat(payment.getExRate()).isEqualByComparingTo(exRate);
        assertThat(payment.getConvertedAmount()).isEqualByComparingTo(convertedAmount);
    }

}