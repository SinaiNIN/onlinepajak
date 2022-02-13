package com.onlinepajak.service;

import com.onlinepajak.exception.FileTypeException;
import com.onlinepajak.exception.FileNullException;
import com.onlinepajak.exception.ResourceNotFoundException;
import com.onlinepajak.model.InvoiceInputData;
import com.onlinepajak.model.InvoiceOutputData;
import com.onlinepajak.model.TaxType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

import java.io.IOException;
import java.util.Set;

import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @InjectMocks
    TransactionService transactionService;

    @Mock
    FileService fileService;

    String textValue = "123, 2021/04/customer1-12212, 2021-04-29T13:15:54, 10.00, GST;" +
            "123, 2021/04/customer1-12213, 2021-04-30T13:15:54, 10.00, GST";

    @Test
    void testValidateFileNotFound() {
        MockMultipartFile file = new MockMultipartFile(
                "filename", "", MediaType.TEXT_PLAIN_VALUE, "".getBytes());
        InvoiceInputData input = new InvoiceInputData(file, TaxType.GST, 123);

        //given
        FileNullException exception = Assertions.assertThrows(FileNullException.class,
                () -> transactionService.createInvoice(input));

        //then
        Assertions.assertTrue(exception.getMessage()
                .contains("reason: file cannot be null"));
    }

    @Test
    void testValidateFileType() {
        MockMultipartFile file = new MockMultipartFile(
                "filename", "test.txt", MediaType.TEXT_PLAIN_VALUE, "".getBytes());
        InvoiceInputData inputData = new InvoiceInputData(file, TaxType.GST, 123);

        //given
        FileTypeException exception = Assertions.assertThrows(FileTypeException.class, () ->
                transactionService.createInvoice(inputData));
        //then
        Assertions.assertTrue(exception.getMessage().contains("reason: file accepted only type: csv"));
    }

    @Test
    void testCreateReceiptSuccessfully() throws IOException {

        MockMultipartFile file = new MockMultipartFile(
                "filename", "test.csv", MediaType.TEXT_PLAIN_VALUE, textValue.getBytes());

        InvoiceInputData inputData = new InvoiceInputData(file, TaxType.GST, 123);

        //given
        when(fileService.getTransactionsStringFromFile(file)).thenReturn(transactionsStringFormFile());
        InvoiceOutputData outputData = new InvoiceOutputData(20.00 + (20 * 0.1), TaxType.GST, 123);

        //when
        InvoiceOutputData invoiceOutputData = transactionService.createInvoice(inputData);

        //then
        Assertions.assertEquals(outputData.getAmount(), invoiceOutputData.getAmount());
    }

    @Test
    void testCreateReceiptNotExistCustomer() {
        MockMultipartFile file = new MockMultipartFile(
                "filename", "test.csv", MediaType.TEXT_PLAIN_VALUE, textValue.getBytes());

        InvoiceInputData inputData = new InvoiceInputData(file, TaxType.GST, 122);

        //given
        ResourceNotFoundException exception = Assertions.assertThrows(ResourceNotFoundException.class, () ->
                transactionService.createInvoice(inputData));

        //then
        Assertions.assertTrue(exception.getMessage().contains("reason: cannot find resource by"));
    }

    private Set<String> transactionsStringFormFile() {
        return Set.of("123, 2021/04/customer1-12212, 2021-04-29T13:15:54, 10.00, GST",
                "123, 2021/04/customer1-12213, 2021-04-30T13:15:54, 10.00, GST");
    }
}