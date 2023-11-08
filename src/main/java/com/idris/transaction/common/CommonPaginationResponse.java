package com.idris.transaction.common;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CommonPaginationResponse<T>{
    private Integer offset;
    private Integer limit;
    private T records;
}
