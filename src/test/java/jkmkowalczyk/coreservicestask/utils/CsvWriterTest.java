package jkmkowalczyk.coreservicestask.utils;

import jkmkowalczyk.coreservicestask.request.RequestDto;
import jkmkowalczyk.coreservicestask.testutils.TestUtil;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;

import static jkmkowalczyk.coreservicestask.utils.NumberFormatUtil.*;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CsvWriterTest {
    private TestUtil testUtil;
    private CsvWriter csvWriter;


    @Before
    public void setUp() {
        csvWriter = new CsvWriter();
        testUtil = new TestUtil();
    }

    @Test
    public void shouldGenerateCsvWithCorrectContentAndReportName() throws IOException {
        //given
        String content = "1";
        File file = csvWriter.generateCsvReport(content, "testContent");
        String content2 = "";

        //when
        BufferedReader reader = Files.newBufferedReader(file.toPath());
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT);
        for (CSVRecord csvRecord : csvParser) {
            content2 = csvRecord.get(0);
        }

        //then
        assertEquals(content, content2);
        assertEquals(file.getPath().replace("\\", "/"), csvWriter.getPathName());

        file.delete();
    }

    @Test
    public void shouldGenerateCsvWithCorrectRequestAndReportName() throws IOException {
        //given
        RequestDto request = testUtil.generateRandomRequestDto();
        ArrayList<RequestDto> requests = new ArrayList<>();
        requests.add(request);
        File file = csvWriter.generateCsvReport(requests, "testRequest");
        RequestDto request2 = new RequestDto.Builder().build();

        //when
        BufferedReader reader = Files.newBufferedReader(file.toPath());
        CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT
                .withFirstRecordAsHeader()
                .withIgnoreHeaderCase());
        for (CSVRecord csvRecord : csvParser) {
            request2 = new RequestDto.Builder()
                    .clientId(csvRecord.get("Client_Id"))
                    .requestId(Long.parseLong(csvRecord.get("Request_Id")))
                    .name(csvRecord.get("Name"))
                    .quantity(Integer.parseInt(csvRecord.get("Quantity")))
                    .price(Double.parseDouble(csvRecord.get("Price")))
                    .build();
        }

        //then
        assertEquals(request, request2);
        assertEquals(file.getPath().replace("\\", "/"), csvWriter.getPathName());

        file.delete();
    }
}
