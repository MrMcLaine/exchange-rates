package ua.exchangerates.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MinfinExchangeRateDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("pointDate")
    private String pointDate;

    @JsonProperty("date")
    private long date;

    @JsonProperty("ask")
    private BigDecimal ask;

    @JsonProperty("bid")
    private BigDecimal bid;

    @JsonProperty("trendAsk")
    private BigDecimal trendAsk;

    @JsonProperty("trendBid")
    private BigDecimal trendBid;

    @JsonProperty("currency")
    private String currency;
}

