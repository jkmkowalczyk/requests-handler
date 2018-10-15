package jkmkowalczyk.coreservicestask.utils;

import jkmkowalczyk.coreservicestask.request.RequestDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

/**
 * This class provides method for loading csv file into the application.
 */
@Component
public
class CsvLoader {

    /**
     * Loads the csv files into the application.
     *
     * @param files list of xml files to load
     * @return list of loaded requests
     */
    public final List<RequestDto> load(final List<File> files) {
        List<RequestDto> requests = new ArrayList<>();

        for (File file : files) {
            try {
                BufferedReader reader = Files.newBufferedReader(file.toPath());
                CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                        .withFirstRecordAsHeader()
                        .withIgnoreHeaderCase());
                for (CSVRecord csvRecord : csvParser) {
                    try {
                        RequestDto request = new RequestDto.Builder()
                                .clientId(csvRecord.get("Client_Id"))
                                .requestId(Long.parseLong(csvRecord.get("Request_Id")))
                                .name(csvRecord.get("Name"))
                                .quantity(Integer.parseInt(csvRecord.get("Quantity")))
                                .price(Double.parseDouble(csvRecord.get("Price")))
                                .build();
                        requests.add(request);
                    } catch (IllegalArgumentException e) {
                        System.err.println("Invalid record: " + csvRecord.getRecordNumber()
                                + " in file: " + file.getName()
                                + ", moving to the next line.");
                    }
                }
            } catch (IOException e) {
                System.err.println("File: " + file.getName() + " not found!");
            }
        }
        return requests;
    }
}
