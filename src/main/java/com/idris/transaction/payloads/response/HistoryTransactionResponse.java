package com.idris.transaction.payloads.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class HistoryTransactionResponse {

    @JsonProperty("invoice_number")
    private String invoiceNumber;

    @JsonProperty("transaction_type")
    private String transactionType;

    @JsonProperty("description")
    private String description;

    @JsonProperty("total_amount")
    private Long totalAmount;

    @JsonProperty("created_on")
    private Date createdOn;
}
