package ua.exchangerates.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ua.exchangerates.domain.ExchangeRate;
import ua.exchangerates.service.ExchangeService;
import ua.exchangerates.service.impl.MinfinExchangeRateServiceImpl;
import ua.exchangerates.service.impl.MonobankExchangeRateServiceImpl;
import ua.exchangerates.service.impl.PrivatbankExchangeRateServiceImpl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/exchange-rates")
public class ExchangeRateController {

    private final List<ExchangeService> exchangeServices;

    public ExchangeRateController(List<ExchangeService> exchangeServices) {
        this.exchangeServices = exchangeServices;
    }

    @GetMapping("/{sourceName}")
    public List<ExchangeRate> getExchangeRates(@PathVariable String sourceName) {
        ExchangeService exchangeService;
        switch (sourceName.toLowerCase()) {
            case "monobank":
                exchangeService = new MonobankExchangeRateServiceImpl();
                break;
            case "privatbank":
                exchangeService = new PrivatbankExchangeRateServiceImpl();
                break;
            case "minfin":
                exchangeService = new MinfinExchangeRateServiceImpl();
                break;
            default:
                throw new IllegalArgumentException("Invalid source name: " + sourceName);
        }
        List<ExchangeRate> exchangeRates = exchangeService.getExchangeRates();
        BigDecimal averageRate = exchangeRates.stream()
                .map(ExchangeRate::getRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(exchangeRates.size()), RoundingMode.HALF_UP);
        exchangeRates.add(new ExchangeRate(null, "Average", sourceName, averageRate,
                LocalDate.now()));
        return exchangeRates;
    }

    @GetMapping("/period/{sourceName}")
    public List<ExchangeRate> getExchangeRatesForPeriod(
            @PathVariable String sourceName,
            @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        ExchangeService exchangeService;
        switch (sourceName.toLowerCase()) {
            case "monobank":
                exchangeService = new MonobankExchangeRateServiceImpl();
                break;
            case "privatbank":
                exchangeService = new PrivatbankExchangeRateServiceImpl();
                break;
            case "minfin":
                exchangeService = new MinfinExchangeRateServiceImpl();
                break;
            default:
                throw new IllegalArgumentException("Invalid source name: " + sourceName);
        }
        List<ExchangeRate> exchangeRates = exchangeService.getExchangeRatesForPeriod(startDate, endDate);
        BigDecimal averageRate = exchangeRates.stream()
                .map(ExchangeRate::getRate)
                .reduce(BigDecimal.ZERO, BigDecimal::add)
                .divide(BigDecimal.valueOf(exchangeRates.size()), RoundingMode.HALF_UP);
        exchangeRates.add(new ExchangeRate(null, "Average", sourceName, averageRate, LocalDate.now()));
        return exchangeRates;
    }
}
