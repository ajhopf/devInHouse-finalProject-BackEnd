package com.example.labmedical.utils;

import com.example.labmedical.repository.model.Log;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class PrintLogUtil {
    private static final ObjectWriter mapper = new ObjectMapper().writerWithDefaultPrettyPrinter();
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";

    private PrintLogUtil() {
    }

    public static void error(Log logSupplier) {
        printResponse(logSupplier, ANSI_RED);
    }

    public static void info(Log logSupplier) {
        printResponse(logSupplier, ANSI_YELLOW);
    }

    public static void success(Log logSupplier) {
        printResponse(logSupplier, ANSI_GREEN);
    }

    private static void printResponse(Log log, String selectedColor) {
        System.out.println(selectedColor);
        try {
            System.out.println(mapper.writeValueAsString(log));
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        System.out.println(ANSI_RESET);
    }

}

