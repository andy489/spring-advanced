package bg.softuni.currencyrestconsumer.service;

import bg.softuni.currencyrestconsumer.model.dto.ExchangeRateDTO;
import bg.softuni.currencyrestconsumer.model.entity.ExchangeRateEntity;

import java.util.List;

public interface CurrencyService {
    void refreshRates(ExchangeRateDTO exchangeRateDTO);

    List<ExchangeRateEntity> getRates();
}
