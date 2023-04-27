package ua.exchangerates.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ua.exchangerates.domain.ExchangeRate;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ExchangeRateRepository extends JpaRepository<ExchangeRate, Long> {
    List<ExchangeRate> findBySourceName(String sourceName);
    List<ExchangeRate> findBySourceNameAndDateBetween(String sourceName, LocalDate startDate, LocalDate endDate);
}
