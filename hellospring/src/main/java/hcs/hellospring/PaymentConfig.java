package hcs.hellospring;

import hcs.hellospring.api.ApiTemplate;
import hcs.hellospring.exrate.CachedExRateProvider;
import hcs.hellospring.exrate.RestClientExRateProvider;
import hcs.hellospring.payment.ExRateProvider;
import hcs.hellospring.payment.PaymentService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.ClientHttpRequestFactories;
import org.springframework.boot.web.client.ClientHttpRequestFactorySettings;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestClient;

import java.io.IOException;
import java.time.Clock;
import java.time.Duration;

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

    @Value("${hello.base-url:https://open.er-api.com}")
    private String baseUrl;

    @Bean
    public ExRateProvider exRateProvider() {
//        return new WebApiExRateProvider(apiTemplate());
//        return new RestTemplateExRateProvider(baseUrl, restTemplate());
        return new RestClientExRateProvider(restClientBuilder(), baseUrl);
    }

    @Bean
    public RestClient.Builder restClientBuilder() {
        return RestClient.builder()
                .requestFactory(ClientHttpRequestFactories.get(ClientHttpRequestFactorySettings.DEFAULTS
                        .withConnectTimeout(Duration.ofSeconds(5L))
                        .withReadTimeout(Duration.ofSeconds(3L))))
                .defaultStatusHandler(defaultResponseErrorHandler());
    }

    @Bean
    public ResponseErrorHandler defaultResponseErrorHandler() {
        return new ResponseErrorHandler() {
            @Override
            public boolean hasError(ClientHttpResponse response) throws IOException {
                return response.getStatusCode().is4xxClientError()
                        || response.getStatusCode().is5xxServerError();
            }

            @Override
            public void handleError(ClientHttpResponse response) throws IOException {
                System.out.println("PaymentConfig.handleError");
                throw response.getStatusCode().is4xxClientError() ?
                        new RuntimeException(response.getStatusText()) :
                        new RuntimeException("");
            }
        };
    }

    //        @Bean
    public RestTemplateBuilder restTemplate() {
        return new RestTemplateBuilder();
    }

    //    @Bean
    public ApiTemplate apiTemplate() {
        return new ApiTemplate();
    }

    @Bean
    public Clock clock() {
        return Clock.systemDefaultZone();
    }
}
