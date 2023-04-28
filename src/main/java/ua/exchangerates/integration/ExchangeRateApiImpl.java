package ua.exchangerates.integration;

import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import ua.exchangerates.domain.ExchangeRate;
import ua.exchangerates.dto.MonobankExchangeRateDto;
import ua.exchangerates.dto.PrivatBankExchangeRateDto;
import ua.exchangerates.repository.ExchangeRateRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class ExchangeRateApiImpl implements ExchangeRateApi {

    private final RestTemplate restTemplate;
    private final ExchangeRateRepository exchangeRateRepository;
    private static final long MIN_TIME_BETWEEN_REQUESTS = 1000; // 1 second

    public ExchangeRateApiImpl(RestTemplate restTemplate, ExchangeRateRepository exchangeRateRepository) {
        this.restTemplate = restTemplate;
        this.exchangeRateRepository = exchangeRateRepository;
    }

    @SneakyThrows
    @Override
    public List<ExchangeRate> getMonoBankExchangeRates() {
        String url = "https://api.monobank.ua/bank/currency";

        long lastRequestTime = System.currentTimeMillis() - MIN_TIME_BETWEEN_REQUESTS; // ensure that first request is sent immediately
        while (true) {
            long timeSinceLastRequest = System.currentTimeMillis() - lastRequestTime;
            if (timeSinceLastRequest < MIN_TIME_BETWEEN_REQUESTS) {
                Thread.sleep(MIN_TIME_BETWEEN_REQUESTS - timeSinceLastRequest);
            }

            ResponseEntity<MonobankExchangeRateDto[]> response = restTemplate.getForEntity(url, MonobankExchangeRateDto[].class);
            if (response.getStatusCode() == HttpStatus.OK) {
                List<ExchangeRate> exchangeRates = Arrays.stream(response.getBody())
                        .map(dto -> new ExchangeRate(
                                null,
                                dto.getCurrencyCodeA(),
                                "Monobank",
                                getAverageRate(dto.getRateBuy(), dto.getRateCross(), dto.getRateSell()),
                                Instant.ofEpochSecond(dto.getDate()).atZone(ZoneId.systemDefault()).toLocalDate()
                        ))
                        .collect(Collectors.toList());

                exchangeRateRepository.saveAll(exchangeRates); // save exchange rates to the database
                return exchangeRates;
            } else if (response.getStatusCode() == HttpStatus.TOO_MANY_REQUESTS) {
                System.out.println("Too many requests. Retrying in " + MIN_TIME_BETWEEN_REQUESTS + " ms...");
                lastRequestTime = System.currentTimeMillis();
            } else {
                throw new RuntimeException("Failed to get exchange rates from Monobank API. Response code: "
                                           + response.getStatusCodeValue());
            }
        }
    }

    @Override
    public List<ExchangeRate> getMinfinExchangeRates() {
/*        LocalDate date = LocalDate.now();
        String url = "https://api.minfin.com.ua/mb/431450378a5b21d26d86c26e74eaa80feda50949/";
        ResponseEntity<MinfinExchangeRateDto[]> response = restTemplate.getForEntity(url, MinfinExchangeRateDto[].class);
        List<ExchangeRate> exchangeRates = Arrays.stream(response.getBody())
                .map(dto -> new ExchangeRate(
                        null,
                        getCurrencyFromStringName(dto.getCurrency()),
                        "Minfin",
                        getAverageRate(dto.getAsk(), BigDecimal.ZERO, dto.getBid()),
                        Instant.ofEpochSecond(dto.getDate()).atZone(ZoneId.systemDefault()).toLocalDate()
                ))
                .collect(Collectors.toList());
        exchangeRateRepository.saveAll(exchangeRates); // save exchange rates to the database
        return exchangeRates;*/
        return null;
    }

    @Override
    public List<ExchangeRate> getPrivatBankExchangeRates() {
        String url = "https://api.privatbank.ua/p24api/pubinfo?exchange&json&coursid=11";
        ResponseEntity<PrivatBankExchangeRateDto[]> response = restTemplate.getForEntity(url, PrivatBankExchangeRateDto[].class);
        List<ExchangeRate> exchangeRates = Arrays.stream(response.getBody())
                .map(dto -> new ExchangeRate(
                        null,
                        getCurrencyFromStringName(dto.getCurrency()),
                        "PrivatBank",
                        getAverageRate(new BigDecimal(dto.getBuyRate()), BigDecimal.ZERO, new BigDecimal(dto.getSaleRate())),
                        LocalDate.now()
                ))
                .collect(Collectors.toList());
        exchangeRateRepository.saveAll(exchangeRates); // save exchange rates to the database
        return exchangeRates;
    }

    private static BigDecimal getAverageRate(BigDecimal rateBuy, BigDecimal rateCross, BigDecimal rateSell) {
        if(!Objects.equals(rateCross, BigDecimal.ZERO)) {
            return rateCross;
        } else {
            return rateBuy.add(rateSell).divide(BigDecimal.valueOf(2));
        }
    }

    private static String getCurrencyFromStringName(String nameCurrency) {
        final String USD_CURRENCY = "840";
        final String EUR_CURRENCY = "978";
        switch (nameCurrency.toLowerCase()){
            case "usd":
                return USD_CURRENCY;
            case "eur":
                return EUR_CURRENCY;
            default:
                throw new RuntimeException("Unknown currency name");
        }
    }
}

