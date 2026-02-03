package bg.softuni.currencyrestconsumer.repo;

import bg.softuni.currencyrestconsumer.model.entity.ExchangeRateEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExchangeRateRepository extends JpaRepository<ExchangeRateEntity, String> {
}

