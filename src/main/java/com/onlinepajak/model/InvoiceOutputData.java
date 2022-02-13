package com.onlinepajak.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class InvoiceOutputData {
    private Double amount;
    private TaxType taxType;
    private Integer customer_id;
}
