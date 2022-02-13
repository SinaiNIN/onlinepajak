package com.onlinepajak.service;

import com.onlinepajak.exception.FileTypeException;
import com.onlinepajak.exception.FileNullException;
import com.onlinepajak.exception.ResourceNotFoundException;
import com.onlinepajak.model.Transaction;
import com.onlinepajak.model.InvoiceInputData;
import com.onlinepajak.model.InvoiceOutputData;
import com.onlinepajak.model.TaxType;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class TransactionService {
    private FileService fileService;

    public InvoiceOutputData createInvoice(InvoiceInputData input) throws IOException {

        validateFile(input.getFile());
        Set<String> transactionsStringFromFile = fileService.getTransactionsStringFromFile(input.getFile());
        Set<Transaction> transactions = getTransactions(transactionsStringFromFile);
        InvoiceOutputData invoiceOutputData = getInvoiceOutput(input.getTaxType(), input.getCustomer_id(), transactions);

        return invoiceOutputData;
    }

    private void validateFile(final MultipartFile file) {
        if (file.getOriginalFilename() == null || file.getOriginalFilename().isEmpty()) {
            throw new FileNullException();
        }
        String fileExtension = StringUtils.getFilenameExtension(file.getOriginalFilename());
        if (!fileExtension.equalsIgnoreCase("csv")) {
            throw new FileTypeException();
        }
    }

    private InvoiceOutputData getInvoiceOutput(final TaxType taxType, final Integer customer_id, final Set<Transaction> transactions) {

        validateInputValue(transactions, customer_id, taxType);

        double totalAmount = transactions.stream()
                .filter(t -> t.getTaxType().equals(taxType) && t.getCustomer_id() == customer_id)
                .mapToDouble(t -> t.getAmount() + (t.getAmount() * 0.10)).sum();
        InvoiceOutputData output = new InvoiceOutputData(totalAmount, taxType, customer_id);
        return output;
    }

    private Set<Transaction> getTransactions(final Set<String> transactions) {
        Set<Transaction> transactionSet = new HashSet<>();
        transactions.forEach(t -> {
            String[] values = t.split(",", 0);
            var invoice = getTransaction(values);
            transactionSet.add(invoice);
        });
        return transactionSet;
    }

    private Transaction getTransaction(String[] values) {
        Transaction transaction = new Transaction();

        transaction.setCustomer_id(Integer.valueOf(values[0].trim()));
        transaction.setInvoice_num(values[1].trim());
        transaction.setTimestamp(LocalDateTime.parse(values[2].trim()));
        transaction.setAmount(Double.valueOf(values[3].trim()));
        transaction.setTaxType(TaxType.valueOf(values[4].trim()));

        return transaction;
    }

    private void validateInputValue(final Set<Transaction> transactions, final Integer customer_id, final TaxType taxType) {
        long totalCustomer = transactions.stream()
                .filter(t -> t.getCustomer_id() == customer_id && t.getTaxType().equals(taxType)).count();

        if (totalCustomer == 0) {
            throw new ResourceNotFoundException(taxType.toString(), customer_id);
        }

    }
}
