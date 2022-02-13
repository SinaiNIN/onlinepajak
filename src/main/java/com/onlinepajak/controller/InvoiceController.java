package com.onlinepajak.controller;

import com.onlinepajak.model.InvoiceInputData;
import com.onlinepajak.model.InvoiceOutputData;
import com.onlinepajak.model.TaxType;
import com.onlinepajak.service.TransactionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("invoice")
@AllArgsConstructor
public class InvoiceController {

    private TransactionService transactionService;

    @PostMapping
    public InvoiceOutputData createReceipt(@RequestParam MultipartFile file,
                                           @RequestParam Integer customer_id,
                                           @RequestParam TaxType taxType) throws IOException {

        return transactionService.createInvoice(new InvoiceInputData(file, taxType, customer_id));
    }
}
