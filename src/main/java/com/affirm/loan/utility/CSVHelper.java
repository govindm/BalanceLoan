package com.affirm.loan.utility;

import com.opencsv.CSVReader;
import com.opencsv.CSVWriter;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.util.List;

public class CSVHelper {
    public static List<String[]> readFromCsvFile(String filename) throws Exception{
        CSVReader csvReader;
        try (Reader reader = new FileReader(ClassLoader.getSystemResource(filename).getFile())) {
            csvReader = new CSVReader(reader);
            csvReader.skip(1); // skip the header line
            List<String[]> data = csvReader.readAll();
            csvReader.close();
            return data;
        }
    }

    public static void writeToCsvFile(List<String[]> data, String[] header, String filename) throws IOException {
        CSVWriter csvWriter = new CSVWriter(new FileWriter(filename),CSVWriter.DEFAULT_SEPARATOR,
                CSVWriter.NO_QUOTE_CHARACTER,CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
        csvWriter.writeNext(header);
        csvWriter.writeAll(data);
        csvWriter.close();
    }
}
