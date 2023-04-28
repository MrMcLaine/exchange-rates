package ua.exchangerates.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.exchangerates.domain.ExchangeRate;
import ua.exchangerates.repository.ExchangeRateRepository;
import ua.exchangerates.service.ExchangeService;

import java.time.LocalDate;
import java.util.List;

@Service
public class MinfinExchangeRateServiceImpl implements ExchangeService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    public MinfinExchangeRateServiceImpl() {
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

