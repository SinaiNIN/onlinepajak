package com.onlinepajak.service;

import com.onlinepajak.exception.NotFoundTransactionValueInFileException;
import com.onlinepajak.model.InvoiceInputData;
import com.onlinepajak.model.TaxType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;

@ExtendWith(MockitoExtension.class)
class FileServiceTest {

    @InjectMocks
    FileService fileService;

    @Test
    void testReadFileNotFoundTransactionValue() {
        MockMultipartFile file = new MockMultipartFile(
                "filename", "test.csv", MediaType.TEXT_PLAIN_VALUE, " ".getBytes());

        InvoiceInputData inputData = new InvoiceInputData(file, TaxType.GST, 123);
        //given
        NotFoundTransactionValueInFileException exception =
                Assertions.assertThrows(NotFoundTransactionValueInFileException.class,
                        () -> fileService.getTransactionsStringFromFile(file));

        //then
        Assertions.assertTrue(exception.getMessage().contains("reason: cannot find resource in File"));
    }

    @Test
    void testGetTransactionsStringFromFile() throws Exception {
        String textValue = "123, 2021/04/customer1-12212, 2021-04-29T13:15:54, 10.00, GST;\n" +
                "123, 2021/04/customer1-12213, 2021-04-30T13:15:54, 10.00, GST";

        MockMultipartFile file = new MockMultipartFile(
                "filename", "test.csv", MediaType.TEXT_PLAIN_VALUE, textValue.getBytes());

        InvoiceInputData inputData = new InvoiceInputData(file, TaxType.GST, 123);

        //when
        var transactionsStringFromFile = fileService.getTransactionsStringFromFile(file);

        //then
        Assertions.assertTrue(transactionsStringFromFile.size() == 2);
    }

}