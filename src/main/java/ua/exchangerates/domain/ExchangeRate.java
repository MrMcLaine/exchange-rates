package ua.exchangerates.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    @Entity
    @Table(name = "exchange_rates")
    public class ExchangeRate {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private Long id;

        @Column(name = "currency_code")
        private String currencyCode;

        @Column(name = "source_name")
        private String sourceName;

        @Column(name = "rate")
        private BigDecimal rate;

        @Column(name = "date")
        private LocalDate date;

}

