package ua.exchangerates.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ExchangeRateDto {

    private String currencyCode;

    private BigDecimal rate;

    private LocalDate date;
}
