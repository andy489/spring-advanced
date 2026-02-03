package bg.softuni.currencyrestconsumer.model.dto;

import java.math.BigDecimal;
import java.util.Map;

public record ExchangeRateDTO(String base, Map<String, BigDecimal> rates) {
}
