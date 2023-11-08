package com.idris.transaction.model;

import com.idris.transaction.common.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Setter
@Getter
@Entity
@Table(name = "sequence")
public class Sequence extends BaseEntity {

    @Column(name = "sequence_name", unique = true)
    private String sequenceName;

    @Column(name = "sequence_number")
    private Long sequenceNumber;
}
