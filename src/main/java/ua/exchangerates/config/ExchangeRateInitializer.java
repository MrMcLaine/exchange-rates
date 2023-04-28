package ua.exchangerates.config;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import ua.exchangerates.integration.ExchangeRateApi;
import ua.exchangerates.repository.ExchangeRateRepository;

import javax.annotation.PostConstruct;

@Component
public class ExchangeRateInitializer {

    private final ExchangeRateApi exchangeRateApi;
    private final ExchangeRateRepository exchangeRateRepository;

    public ExchangeRateInitializer(ExchangeRateApi exchangeRateApi, ExchangeRateRepository exchangeRateRepository) {
        this.exchangeRateApi = exchangeRateApi;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @PostConstruct
    public void initialize() {
        // load exchange rates from APIs and save to database
        exchangeRateApi.getMonoBankExchangeRates();
        exchangeRateApi.getMinfinExchangeRates();
        exchangeRateApi.getPrivatBankExchangeRates();
    }

    @Scheduled(cron = "0 0 * * * *") // run every hour
    public void syncExchangeRates() {
        // load latest exchange rates from APIs and save to database
        exchangeRateApi.getMonoBankExchangeRates();
        exchangeRateApi.getMinfinExchangeRates();
        exchangeRateApi.getPrivatBankExchangeRates();
    }
}

