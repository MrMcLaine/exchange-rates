package ua.exchangerates;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import ua.exchangerates.domain.ExchangeRate;
import ua.exchangerates.repository.ExchangeRateRepository;

import java.math.BigDecimal;
import java.time.LocalDate;

@SpringBootApplication
public class ExchangeRatesApplication implements CommandLineRunner {

	@Autowired
	ExchangeRateRepository repository;

	public static void main(String[] args) {

		SpringApplication.run(ExchangeRatesApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		ExchangeRate exchangeRate = new ExchangeRate();
		exchangeRate.setDate(LocalDate.now());
		exchangeRate.setRate(BigDecimal.valueOf(10.00));
		exchangeRate.setCurrencyCode("test");
		exchangeRate.setSourceName("test");

		repository.save(exchangeRate);
	}
}
