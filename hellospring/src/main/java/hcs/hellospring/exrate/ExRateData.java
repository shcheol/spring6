package hcs.hellospring.exrate;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;
import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
public record ExRateData(String result, @Nonnull Map<String, BigDecimal> rates) {
}
