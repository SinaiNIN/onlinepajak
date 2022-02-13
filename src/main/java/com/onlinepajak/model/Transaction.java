package com.onlinepajak.model;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode
public class Transaction {
    private Integer customer_id;
    private String invoice_num;
    private LocalDateTime timestamp;
    private Double amount;
    private TaxType taxType;

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp.withNano(0);
    }
}
