package ua.exchangerates.controller;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ua.exchangerates.dto.ExchangeRateDto;
import ua.exchangerates.service.ExchangeService;
import ua.exchangerates.service.impl.ExchangeRateServiceImpl;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/exchange-rates")
public class ExchangeRateController {

    private final ExchangeService service;

    public ExchangeRateController(ExchangeRateServiceImpl service) {
        this.service = service;
    }

    @GetMapping("/")
    public List<ExchangeRateDto> getExchangeRates() {
        return service.getExchangeRates();
    }

    @GetMapping("/period/")
    public List<ExchangeRateDto> getExchangeRatesForPeriod(
            @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        return service.getAllBetween(startDate, endDate);
    }
}
