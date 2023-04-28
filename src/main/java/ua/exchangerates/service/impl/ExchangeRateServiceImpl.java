package ua.exchangerates.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ua.exchangerates.domain.ExchangeRate;
import ua.exchangerates.dto.ExchangeRateDto;
import ua.exchangerates.repository.ExchangeRateRepository;
import ua.exchangerates.service.ExchangeService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ExchangeRateServiceImpl implements ExchangeService {

    @Autowired
    private ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateServiceImpl() {
    }

    @Override
    public List<ExchangeRateDto> getExchangeRates() {
        List<ExchangeRate> exchangeRates = exchangeRateRepository.findAll();

        Map<String, List<ExchangeRate>> ratesByCurrency = exchangeRates.stream()
                .collect(Collectors.groupingBy(ExchangeRate::getCurrencyCode));

        return getListFromMapByCurrency(ratesByCurrency);
    }


    @Override
    public List<ExchangeRateDto> getAllBetween(LocalDate startDate, LocalDate endDate) {

        List<ExchangeRateDto> averageRates = new ArrayList<>();

        List<ExchangeRate> exchangeRates = exchangeRateRepository.findByDateBetween(startDate, endDate);
        Map<LocalDate, List<ExchangeRate>> groupByDate = exchangeRates.stream()
                .collect(Collectors.groupingBy(ExchangeRate::getDate));

        for (Map.Entry<LocalDate, List<ExchangeRate>> entry : groupByDate.entrySet()) {
            List<ExchangeRate> ratesForDate = entry.getValue();

            Map<String, List<ExchangeRate>> ratesByCurrency = ratesForDate.stream()
                    .collect(Collectors.groupingBy(ExchangeRate::getCurrencyCode));

            List<ExchangeRateDto> tempList = getListFromMapByCurrency(ratesByCurrency);
            averageRates.addAll(tempList);
        }
        return averageRates;
    }

    private static List<ExchangeRateDto> getListFromMapByCurrency(Map<String, List<ExchangeRate>> ratesByCurrency) {
        return ratesByCurrency.entrySet().stream()
                .map(entry -> {
                    String currencyCode = entry.getKey();
                    List<ExchangeRate> rates = entry.getValue();

                    BigDecimal sum = BigDecimal.ZERO;
                    for (ExchangeRate rate : rates) {
                        sum = sum.add(rate.getRate());
                    }

                    BigDecimal average = sum.divide(new BigDecimal(rates.size()), RoundingMode.HALF_UP);
                    return new ExchangeRateDto(currencyCode, average, rates.get(0).getDate());
                })
                .sorted(Comparator.comparing(ExchangeRateDto::getCurrencyCode))
                .collect(Collectors.toList());
    }
}

