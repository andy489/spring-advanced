package bg.softuni.currencyrestconsumer.service.impl;

import bg.softuni.currencyrestconsumer.model.dto.ExchangeRateDTO;
import bg.softuni.currencyrestconsumer.model.entity.ExchangeRateEntity;
import bg.softuni.currencyrestconsumer.repo.ExchangeRateRepository;
import bg.softuni.currencyrestconsumer.service.CurrencyService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class CurrencyServerImpl implements CurrencyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CurrencyServerImpl.class);

    private final ExchangeRateRepository exchangeRateRepository;

    public CurrencyServerImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public List<ExchangeRateEntity> getRates(){
        return exchangeRateRepository.findAll().stream().toList();
    }

    @Override
    public void refreshRates(ExchangeRateDTO exchangeRateDTO) {
        LOGGER.info("Exchange rates received {}", exchangeRateDTO);

        BigDecimal EUR_TO_USD = getExchangeRate(exchangeRateDTO, "EUR", "USD").orElse(null);
        BigDecimal EUR_TO_GBP = getExchangeRate(exchangeRateDTO, "EUR", "GBP").orElse(null);
        BigDecimal EUR_TO_CHF = getExchangeRate(exchangeRateDTO, "EUR", "CHF").orElse(null);

        if(EUR_TO_USD !=null) {
            ExchangeRateEntity exchangeRateEntity = new ExchangeRateEntity()
                    .setCurrency("USD").setRate(EUR_TO_USD);

            exchangeRateRepository.save(exchangeRateEntity);
        } else {
            LOGGER.error("Unable to get exchange rate from EUR TO USD");
        }

        if(EUR_TO_GBP !=null) {
            ExchangeRateEntity exchangeRateEntity = new ExchangeRateEntity()
                    .setCurrency("GBP").setRate(EUR_TO_GBP);

            exchangeRateRepository.save(exchangeRateEntity);
        } else {
            LOGGER.error("Unable to get exchange rate from EUR TO GBP");
        }

        if(EUR_TO_CHF !=null) {
            ExchangeRateEntity exchangeRateEntity = new ExchangeRateEntity()
                    .setCurrency("CHF").setRate(EUR_TO_CHF);

            exchangeRateRepository.save(exchangeRateEntity);
        } else {
            LOGGER.error("Unable to get exchange rate from EUR TO CHF");
        }

        LOGGER.info("Rates refreshed...");
    }

    private static Optional<BigDecimal> getExchangeRate(ExchangeRateDTO exchangeRateDTO, String from, String to) {
        Objects.requireNonNull(from, "from currency cannot be null");
        Objects.requireNonNull(to, "to currency cannot be null");

        if (Objects.equals(from, to)) {
            return Optional.of(BigDecimal.ONE);
        }

        if (Objects.equals(from, exchangeRateDTO.base())) {
            if (exchangeRateDTO.rates().containsKey(to)) {
                return Optional.of(exchangeRateDTO.rates().get(to));
            }
        } else if (Objects.equals(to, exchangeRateDTO.base())) {
            if (exchangeRateDTO.rates().containsKey(from)) {
                return Optional.of(BigDecimal.ONE
                        .divide(exchangeRateDTO.rates().get(from), 3, RoundingMode.DOWN));
            }
        } else if (exchangeRateDTO.rates().containsKey(from) &&
                exchangeRateDTO.rates().containsKey(to)) {
            return Optional.of(exchangeRateDTO.rates().get(to)
                    .divide(exchangeRateDTO.rates().get(from),3, RoundingMode.DOWN));
        }

        return Optional.empty();
    }
}
