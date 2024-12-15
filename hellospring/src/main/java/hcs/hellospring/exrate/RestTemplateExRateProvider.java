package hcs.hellospring.exrate;

import hcs.hellospring.payment.ExRateProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;

public class RestTemplateExRateProvider implements ExRateProvider  {
	private final RestTemplate restTemplate;

	public RestTemplateExRateProvider(
			@Value("${hello.base-url}")String baseUrl,
			RestTemplateBuilder restTemplate) {
		this.restTemplate = restTemplate.rootUri(baseUrl).build();
	}

	@Override
	public BigDecimal getExRate(String currency) {
		String url = "/v6/latest/" + currency;
		return restTemplate.getForObject(url, ExRateData.class).rates().get("KRW");
	}
}
