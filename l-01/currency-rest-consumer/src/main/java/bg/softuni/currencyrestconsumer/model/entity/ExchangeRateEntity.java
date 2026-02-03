package bg.softuni.currencyrestconsumer.model.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import jakarta.annotation.Nonnull;

import java.math.BigDecimal;

@Entity
@Table(name="exchange_rates")
@Getter
@Setter
@Accessors(chain = true)
public class ExchangeRateEntity {

    @Id
    @Nonnull
    private String currency;

    @Nonnull
    private BigDecimal rate;
}
