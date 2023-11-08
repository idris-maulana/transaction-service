package com.idris.transaction.model;

import com.idris.transaction.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;

@Setter
@Getter
@Entity
@Table(name = "transaction_histories")
public class History extends BaseEntity {

    @Column(name = "invoice_number", unique = true)
    private String invoiceNumber;

    @Column(name = "transaction_type")
    private String transactionType;

    @Column(name = "description")
    private String description;

    @Column(name = "total_amount")
    private Long totalAmount;

    @Column(name = "created_on")
    private Date createdOn;

    @Column(name = "user_id")
    private Long userId;
}
