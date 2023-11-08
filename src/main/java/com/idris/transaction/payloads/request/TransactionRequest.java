package com.idris.transaction.payloads.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Setter
@Getter

public class TransactionRequest {

    @JsonProperty("service_code")
    @NotNull(message = "[101] top_up_amount tidak boleh null")
    @NotEmpty(message = "[102] top_up_amount tidak boleh kosong")
    private String serviceCode;
}
