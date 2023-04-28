package ua.exchangerates.service;

import ua.exchangerates.dto.ExchangeRateDto;

import java.time.LocalDate;
import java.util.List;

public interface ExchangeService {

    List<ExchangeRateDto> getExchangeRates();

    List<ExchangeRateDto> getAllBetween(LocalDate startDate, LocalDate endDate);
}
