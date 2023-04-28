package ua.exchangerates.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MonobankExchangeRateDto {
    @JsonProperty("currencyCodeA")
    private String currencyCodeA;

    @JsonProperty("currencyCodeB")
    private String currencyCodeB;

    @JsonProperty("date")
    private long date;

    @JsonProperty("rateBuy")
    private BigDecimal rateBuy;

    @JsonProperty("rateCross")
    private BigDecimal rateCross;

    @JsonProperty("rateSell")
    private BigDecimal rateSell;
}

