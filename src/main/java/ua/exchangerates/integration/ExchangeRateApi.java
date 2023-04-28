package ua.exchangerates.integration;

import ua.exchangerates.domain.ExchangeRate;

import java.util.List;

public interface ExchangeRateApi {
    List<ExchangeRate> getMonoBankExchangeRates();
    List<ExchangeRate> getMinfinExchangeRates();
    List<ExchangeRate> getPrivatBankExchangeRates();
}
