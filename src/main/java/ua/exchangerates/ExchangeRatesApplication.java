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
	public void run(String... args) {
		ExchangeRate exchangeRate1 = new ExchangeRate("840", "TestBank",
				new BigDecimal("38.00"), LocalDate.of(2023, 4,27));
		ExchangeRate exchangeRate2 = new ExchangeRate("978", "TestBank",
				new BigDecimal("39.50"), LocalDate.of(2023,4,27));
		ExchangeRate exchangeRate3 = new ExchangeRate("840", "TestBank",
				new BigDecimal("38.15"), LocalDate.of(2023, 4,26));
		ExchangeRate exchangeRate4 = new ExchangeRate("978", "TestBank",
				new BigDecimal("39.80"), LocalDate.of(2023,4,26));
		ExchangeRate exchangeRate5 = new ExchangeRate("840", "TestBank",
				new BigDecimal("38.20"), LocalDate.of(2023, 4,25));
		ExchangeRate exchangeRate6 = new ExchangeRate("978", "TestBank",
				new BigDecimal("39.50"), LocalDate.of(2023,4,25));

		repository.save(exchangeRate1);
		repository.save(exchangeRate2);
		repository.save(exchangeRate3);
		repository.save(exchangeRate4);
		repository.save(exchangeRate5);
		repository.save(exchangeRate6);
	}
}
