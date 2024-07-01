package hcs.hellospring.api;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import hcs.hellospring.exrate.ExRateData;

import java.math.BigDecimal;

public class ErApiExRateExtractor implements ExRateExtractor{
	@Override
	public BigDecimal extract(String response) throws JsonProcessingException {
		ObjectMapper om = new ObjectMapper();
		ExRateData exRateData = om.readValue(response, ExRateData.class);
		return exRateData.rates().get("KRW");
	}
}
