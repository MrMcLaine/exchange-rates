package ua.exchangerates.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PrivatBankExchangeRateDto {
    @JsonProperty("ccy")
    private String currency;

    @JsonProperty("base_ccy")
    private String baseCurrency;

    @JsonProperty("buy")
    private String buyRate;

    @JsonProperty("sale")
    private String saleRate;
}

