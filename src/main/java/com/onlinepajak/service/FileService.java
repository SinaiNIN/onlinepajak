package com.onlinepajak.service;

import com.onlinepajak.exception.NotFoundTransactionValueInFileException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileService {

    public Set<String> getTransactionsStringFromFile(final MultipartFile file) throws IOException {
        Set<String> transactions = new HashSet<>();

        StringTokenizer tokenizer = readFile(file);
        while (tokenizer.hasMoreElements()) {
            transactions.add(tokenizer.nextToken());
        }
        return transactions;
    }

    private StringTokenizer readFile(final MultipartFile file) throws IOException {

        BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));
        String line = reader.lines().collect(Collectors.joining());
        if (line == null || line.isEmpty() || line.isBlank()) {
            throw new NotFoundTransactionValueInFileException(file.getOriginalFilename());
        }
        return new StringTokenizer(line, ";");
    }
}
