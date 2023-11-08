package com.idris.transaction.payloads.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter
public class TopUpBalanceRequest {

    @JsonProperty("top_up_amount")
    @NotNull(message = "[101] top_up_amount tidak boleh null")
    @NotEmpty(message = "[102] top_up_amount tidak boleh kosong")
    @Min(value = 0, message = "Paramter amount hanya boleh angka dan tidak boleh lebih kecil dari 0")
    private Long topUpAmmount;
}
