package ua.exchangerates.service.impl;

import org.springframework.stereotype.Service;
import ua.exchangerates.domain.ExchangeRate;
import ua.exchangerates.repository.ExchangeRateRepository;
import ua.exchangerates.service.ExchangeService;

import java.time.LocalDate;
import java.util.List;

@Service
public class PrivatbankExchangeRateServiceImpl implements ExchangeService {

    private final ExchangeRateRepository exchangeRateRepository;

    public PrivatbankExchangeRateServiceImpl(ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @Override
    public List<ExchangeRate> getExchangeRates() {
        return exchangeRateRepository.findBySourceName("Privatbank");
    }

    @Override
    public List<ExchangeRate> getExchangeRatesForPeriod(LocalDate startDate, LocalDate endDate) {
        return exchangeRateRepository.findBySourceNameAndDateBetween("Privatbank", startDate, endDate);
    }
}

