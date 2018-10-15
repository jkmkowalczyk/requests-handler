package jkmkowalczyk.coreservicestask.utils;

import jkmkowalczyk.coreservicestask.request.RequestDto;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.springframework.stereotype.Component;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.text.NumberFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static jkmkowalczyk.coreservicestask.utils.NumberFormatUtil.setUpNumberFormat;


/**
 * This class provides methods for generating csv reports with specified data.
 */
@Component
public
class CsvWriter {

    private String pathName;


    /**
     * Gets path name for the report file.
     *
     * @return report file path name
     */
    final String getPathName() {
        return pathName;
    }

    /**
     * Generates csv report with provided content.
     *
     * @param content  content to save - objects of the following type(s) are allowed
     *                 {@link List <RequestDto> }
     *                 {@link String }
     * @param fileName initial name of the report file
     * @return generated file
     */
    public final File generateCsvReport(final Object content, final String fileName) {
        NumberFormat numberFormat = setUpNumberFormat();
        setPathName(fileName, LocalDateTime.now());
        File file = new File(pathName);
        generateReportsLocation();

        try {
            CSVPrinter csvPrinter = null;


            BufferedWriter writer = Files.newBufferedWriter(file.toPath());
            if (content.getClass().isAssignableFrom(ArrayList.class)) {
                ArrayList<RequestDto> requests = (ArrayList) content;
                csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT
                        .withHeader("Client_Id", "Request_Id", "Name", "Quantity", "Price"));
                for (RequestDto request : requests) {
                    csvPrinter.printRecord(request.getClientId(),
                            request.getRequestId(),
                            request.getName(),
                            request.getQuantity(),
                            numberFormat.format(request.getPrice()));
                }
            } else if (content.getClass().isAssignableFrom(String.class)) {
                csvPrinter = new CSVPrinter(writer, CSVFormat.DEFAULT);
                csvPrinter.printRecord(content);
            }
            if (csvPrinter != null) {
                csvPrinter.flush();
                csvPrinter.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }

    /**
     * Sets up path name for the report file.
     *
     * @param fileName initial name of the file
     * @param now      datetime value for file name
     */
    private void setPathName(final String fileName, final LocalDateTime now) {
        this.pathName = "./reports/" + fileName + " ("
                + now.format(DateTimeFormatter.ofPattern("dd.MM.yyyy 'T'HH-mm-ss")) + ").csv";
    }

    /**
     * Creates 'reports' directory if it does not exist.
     */
    private void generateReportsLocation() {
        File reports = new File("./reports");
        if (!reports.exists()) {
            reports.mkdirs();
        }
    }

}