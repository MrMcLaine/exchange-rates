package ua.exchangerates.service.impl;

import org.springframework.stereotype.Service;
import ua.exchangerates.domain.ExchangeRate;
import ua.exchangerates.repository.ExchangeRateRepository;
import ua.exchangerates.service.ExchangeService;

import java.time.LocalDate;
import java.util.List;

@Service
public class MinfinExchangeRateServiceImpl implements ExchangeService {

    private final ExchangeRateRepository exchangeRateRepository;

    public MinfinExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public List<ExchangeRate> getExchangeRates() {
        return exchangeRateRepository.findBySourceName("Minfin");
    }

    @Override
    public List<ExchangeRate> getExchangeRatesForPeriod(LocalDate startDate, LocalDate endDate) {
        return exchangeRateRepository.findBySourceNameAndDateBetween("Minfin", startDate, endDate);
    }
}

