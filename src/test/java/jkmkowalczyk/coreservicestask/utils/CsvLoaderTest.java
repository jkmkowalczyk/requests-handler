package jkmkowalczyk.coreservicestask.utils;

import jkmkowalczyk.coreservicestask.request.RequestDto;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CsvLoaderTest {

    private CsvLoader csvLoader;
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalErr = System.err;

    @Before
    public void setUp() {
        csvLoader = new CsvLoader();
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setErr(originalErr);
    }

    @Test
    public void shouldLoadThreeRecordsWhenLoadingFileWithThreeValidRecords() {
        File file = new File("src\\test\\java\\jkmkowalczyk\\coreservicestask\\valid.csv");
        List<RequestDto> loadedRequests = csvLoader.load(Arrays.asList(file));
        assertEquals(loadedRequests.size(), 3);
    }

    @Test
    public void shouldLoadTwoRecordsWhenLoadingFileWithOneInvalidRecordAndTwoValidRecords() {
        File file = new File("src\\test\\java\\jkmkowalczyk\\coreservicestask\\invalidSecondRecord.csv");
        List<RequestDto> loadedRequests = csvLoader.load(Arrays.asList(file));
        assertEquals(loadedRequests.size(), 2);
        assertEquals("Invalid record: 2 in file: invalidSecondRecord.csv, moving to the next line.", errContent.toString().trim());
    }

    @Test
    public void shouldCatchAnExceptionWhenProvidedNonExistingFile() {
        csvLoader.load(Arrays.asList(new File("nonexistingfile.csv")));
        assertEquals("File: nonexistingfile.csv not found!", errContent.toString().trim());
    }
}
