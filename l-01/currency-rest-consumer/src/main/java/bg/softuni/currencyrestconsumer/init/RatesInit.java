package bg.softuni.currencyrestconsumer.init;

import bg.softuni.currencyrestconsumer.config.OpenExchangeRateConfig;
import bg.softuni.currencyrestconsumer.model.dto.ExchangeRateDTO;
import bg.softuni.currencyrestconsumer.service.CurrencyService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Component
public class RatesInit implements CommandLineRunner {

    private final OpenExchangeRateConfig openExchangeRateConfig;

    private final RestTemplate restTemplate;

    private final CurrencyService currencyService;

    public RatesInit(OpenExchangeRateConfig openExchangeRateConfig, RestTemplate restTemplate,
                     CurrencyService currencyService) {
        this.openExchangeRateConfig = openExchangeRateConfig;
        this.restTemplate = restTemplate;
        this.currencyService = currencyService;
    }

    @Override
    public void run(String... args) {
        if (openExchangeRateConfig.isEnabled()) {
            String openExchangeRateUrlTemplate = openExchangeRateConfig.getSchema() +
                    "://" +
                    openExchangeRateConfig.getHost() +
                    openExchangeRateConfig.getPath() +
                    "?app_id={app_id}&symbols={symbols}";

            Map<String, String> requestParams = Map.of(
                    "app_id", openExchangeRateConfig.getAppId(),
                    "symbols", String.join(",", openExchangeRateConfig.getSymbols())
            );

            ExchangeRateDTO exchangeRateDTO = restTemplate
                    .getForObject(openExchangeRateUrlTemplate, ExchangeRateDTO.class, requestParams);

            currencyService.refreshRates(exchangeRateDTO);
        }
    }
}
