package hcs.hellospring.exrate;

import hcs.hellospring.payment.ExRateProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestClient;

import java.math.BigDecimal;

//@Component
public class RestClientExRateProvider implements ExRateProvider {

    private final RestClient restClient;

    public RestClientExRateProvider(RestClient.Builder builder,
                                    @Value("${hello.base-url}") String baseUrl) {
        this.restClient = builder.baseUrl(baseUrl).build();
    }

    @Override
    public BigDecimal getExRate(String currency) {
        return restClient.get()
                .uri("/v6/latest/{currency}", currency)
                .retrieve()
                .body(ExRateData.class).rates().get("KRW");
//                .exchange(((clientRequest, clientResponse) -> {
//                    if (clientResponse.getStatusCode().is4xxClientError()) {
//                        System.out.println("clientRequest = " + clientResponse.getStatusText());
//                        throw new IllegalArgumentException();
//                    }
//                    if (clientResponse.getStatusCode().is5xxServerError()){
//                        System.out.println("clientRequest = " + clientResponse.getStatusText());
//                        throw new IllegalStateException();
//                    }
//                    return clientResponse;
//                }))
//                .bodyTo(ExRateData.class).rates().get("KRW");
    }
}
