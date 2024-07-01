package hcs.hellospring;

import hcs.hellospring.api.ApiTemplate;
import hcs.hellospring.exrate.CachedExRateProvider;
import hcs.hellospring.exrate.RestTemplateExRateProvider;
import hcs.hellospring.payment.ExRateProvider;
import hcs.hellospring.exrate.WebApiExRateProvider;
import hcs.hellospring.payment.PaymentService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Clock;

@Configuration
public class PaymentConfig {
    @Bean
    public PaymentService paymentService() {
        return new PaymentService(cachedExRateProvider(), clock());
    }

    @Bean
    public ExRateProvider cachedExRateProvider() {
        return new CachedExRateProvider(exRateProvider());
    }

    @Bean
    public ExRateProvider exRateProvider() {
//        return new WebApiExRateProvider(apiTemplate());
        return new RestTemplateExRateProvider(restTemplate());
    }

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Bean
	public ApiTemplate apiTemplate() {
		return new ApiTemplate();
	}

	@Bean
    public Clock clock(){
        return Clock.systemDefaultZone();
    }
}
