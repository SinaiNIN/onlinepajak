package com.onlinepajak.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@AllArgsConstructor
public class InvoiceInputData {
    private MultipartFile file;
    private TaxType taxType;
    private Integer customer_id;
}
