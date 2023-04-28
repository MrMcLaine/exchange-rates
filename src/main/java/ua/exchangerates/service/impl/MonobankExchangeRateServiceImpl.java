package ua.exchangerates.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.exchangerates.domain.ExchangeRate;
import ua.exchangerates.repository.ExchangeRateRepository;
import ua.exchangerates.service.ExchangeService;

import java.time.LocalDate;
import java.util.List;

@Service
public class MonobankExchangeRateServiceImpl implements ExchangeService {

    @Autowired
    private ExchangeRateRepository repository;

    public MonobankExchangeRateServiceImpl() {
    }

    @Override
    public List<ExchangeRate> getExchangeRates() {
        return repository.findBySourceName("Monobank");
    }

    @Override
    public List<ExchangeRate> getExchangeRatesForPeriod(LocalDate startDate, LocalDate endDate) {
        return repository.findBySourceNameAndDateBetween("Monobank", startDate, endDate);
    }
}

