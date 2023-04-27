package ua.exchangerates.service;

import ua.exchangerates.domain.ExchangeRate;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeService {

    List<ExchangeRate> getExchangeRates();

    List<ExchangeRate> getExchangeRatesForPeriod(LocalDate startDate, LocalDate endDate);

}
