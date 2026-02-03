package bg.softuni.currencyrestconsumer.web;

import bg.softuni.currencyrestconsumer.model.entity.ExchangeRateEntity;
import bg.softuni.currencyrestconsumer.service.CurrencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ExchangeRatesController {

    private final CurrencyService currencyService;

    public ExchangeRatesController(CurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    @GetMapping
    public ResponseEntity<List<ExchangeRateEntity>> getRates() {
        List<ExchangeRateEntity> rates = currencyService.getRates();
        return ResponseEntity.ok(rates);
    }
}
