package hcs.hellospring.learningtest;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.*;

public class ClockTest {
    //Clock을 이용해 LocalDateTime.now?
    @Test
    void clock() throws InterruptedException {
        Clock clock = Clock.systemDefaultZone();
        LocalDateTime dt1 = LocalDateTime.now(clock);
        TimeUnit.SECONDS.sleep(1);
        LocalDateTime dt2 = LocalDateTime.now(clock);

        System.out.println(dt1);
        System.out.println(dt2);
        Assertions.assertThat(dt2).isAfter(dt1);


    }
    //Clock을 Test에서 사용할 때 내가 원하는 시간을 지정해서 현재시간을 가져오게 할 수 있는가
    @Test
    void fixedClock() throws InterruptedException {
        Clock clock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        LocalDateTime dt1 = LocalDateTime.now(clock);
        TimeUnit.SECONDS.sleep(1);
        LocalDateTime dt2 = LocalDateTime.now(clock);
        LocalDateTime dt3 = LocalDateTime.now(clock).plusHours(1);

        Assertions.assertThat(dt2).isEqualTo(dt1);
        Assertions.assertThat(dt3).isEqualTo(dt1.plusHours(1));
    }
}
