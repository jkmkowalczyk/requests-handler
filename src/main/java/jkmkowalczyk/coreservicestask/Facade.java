package jkmkowalczyk.coreservicestask;

import jkmkowalczyk.coreservicestask.exception.ClientNotFoundException;
import jkmkowalczyk.coreservicestask.request.RequestDto;
import jkmkowalczyk.coreservicestask.request.RequestService;
import jkmkowalczyk.coreservicestask.utils.CsvLoader;
import jkmkowalczyk.coreservicestask.utils.CsvWriter;
import jkmkowalczyk.coreservicestask.utils.XmlLoader;
import org.springframework.stereotype.Component;

import java.io.File;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

import static jkmkowalczyk.coreservicestask.utils.NumberFormatUtil.setUpNumberFormat;


/**
 * This class provides methods for running the application.
 */
@Component
public class Facade {
    private final XmlLoader xmlLoader;
    private final CsvWriter csvWriter;
    private final RequestService requestService;
    private final CsvLoader csvLoader;

    private NumberFormat numberFormat;

    public Facade(final XmlLoader xmlLoader,
                  final CsvLoader csvLoader,
                  final CsvWriter csvWriter,
                  final RequestService requestService) {
        this.xmlLoader = xmlLoader;
        this.csvLoader = csvLoader;
        this.csvWriter = csvWriter;
        this.requestService = requestService;
    }

    /**
     * Processes provided list of files to finally save them into the database.
     * <p>
     * Firstly, runs through received files to determine, which files are csv and which are xml.
     * Then loads the data held in the files into the application memory
     * and finally saves every record into the database.
     * </p>
     *
     * @param files list of files to be saved
     */
    public final void processFiles(final List<File> files) {
        List<File> csvFiles = findCsvFiles(files);
        List<File> xmlFiles = findXmlFiles(files);

        List<RequestDto> requests = new ArrayList<>();
        System.out.println("\n\nLoading files...");
        if (xmlFiles.size() > 0) {
            List<RequestDto> xmlRequests = xmlLoader.load(xmlFiles);
            requests.addAll(xmlRequests);
        }

        if (csvFiles.size() > 0) {
            List<RequestDto> csvRequests = csvLoader.load(csvFiles);
            requests.addAll(csvRequests);
        }

        saveRequests(requests);
    }

    /**
     * Displays the main menu and performs specified actions based on user's input.
     */
    final void showMenu() {
        numberFormat = setUpNumberFormat();
        boolean display = true;
        int option;
        String clientId;

        while (display) {
            Scanner optionScanner = new Scanner(System.in);
            Scanner clientIdScanner = new Scanner(System.in);
            Scanner reportScanner = new Scanner(System.in);

            System.out.println("\n\nChoose an option:");
            System.out.println("1: show total number of requests.");
            System.out.println("2: show number of requests for client.");
            System.out.println("3: show total price of requests.");
            System.out.println("4: show total price of requests for client.");
            System.out.println("5: show list of requests.");
            System.out.println("6: show list of requests for client.");
            System.out.println("7: show average price of requests.");
            System.out.println("8: show average price of requests for client.");
            System.out.println("9: exit application.");
            System.out.print("\nEnter a valid number: ");

            try {
                option = optionScanner.nextInt();
            } catch (Exception e) {
                option = 0;
            }

            switch (option) {
                case 1:
                    generateCsvReport(countRequests(),
                            "Number of requests", reportScanner);
                    break;
                case 2:
                    clientId = readClientId(clientIdScanner);
                    try {
                        generateCsvReport(countRequestsForClient(clientId),
                                "Number of requests for client "
                                        + clientId, reportScanner);
                    } catch (ClientNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 3:
                    generateCsvReport(calculateTotalPrice(),
                            "Total price", reportScanner);
                    break;
                case 4:
                    clientId = readClientId(clientIdScanner);
                    try {
                        generateCsvReport(calculateTotalPriceForClient(clientId),
                                "Total price for client "
                                        + clientId, reportScanner);
                    } catch (ClientNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 5:
                    generateCsvReport(generateRequestList(),
                            "Requests", reportScanner);
                    break;
                case 6:
                    clientId = readClientId(clientIdScanner);
                    try {
                        generateCsvReport(generateRequestListForClient(clientId),
                                "Requests for client "
                                        + clientId, reportScanner);
                    } catch (ClientNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 7:
                    generateCsvReport(calculateAvgPrice(),
                            "Average price", reportScanner);
                    break;
                case 8:
                    clientId = readClientId(clientIdScanner);
                    try {
                        generateCsvReport(calculateAvgPriceForClient(clientId),
                                "Average price for client "
                                        + clientId, reportScanner);
                    } catch (ClientNotFoundException e) {
                        System.err.println(e.getMessage());
                    }
                    break;
                case 9:
                    display = false;
                    break;
                default:
                    System.err.println("Invalid input!");
            }
        }
    }

    /**
     * Retrieves all requests from the database and prints theirs quantity.
     *
     * @return the amount of all requests
     */
    private String countRequests() {
        String size = String.valueOf(requestService.findAll().size());
        System.out.println("Total number of requests: " + size + ".");
        return size;
    }

    /**
     * Retrieves requests from the database for specified client and prints theirs quantity.
     *
     * @param clientId clientId to filter by
     * @return the amount of requests for specified client
     * @throws ClientNotFoundException when no requests are found for specified client
     */
    private String countRequestsForClient(final String clientId)
            throws ClientNotFoundException {
        String size = String.valueOf(requestService.findByClientId(clientId).size());
        System.out.println("Number of requests for client '" + clientId + "': " + size + ".");
        return size;
    }

    /**
     * Retrieves all requests from the database and prints theirs total price.
     *
     * @return the total price for all requests
     */
    private String calculateTotalPrice() {
        String totalPrice = numberFormat.format(requestService.findAll().stream()
                .mapToDouble(RequestDto::getPrice).sum());
        System.out.println("Total price: " + totalPrice + ".");
        return totalPrice;
    }

    /**
     * Retrieves requests from the database for specified client and prints theirs total price.
     *
     * @param clientId clientId to filter by
     * @return Tte total price of requests for specified client
     * @throws ClientNotFoundException when no requests are found for specified client
     */
    private String calculateTotalPriceForClient(final String clientId)
            throws ClientNotFoundException {
        String totalPrice = numberFormat.format(requestService.findByClientId(clientId).stream()
                .mapToDouble(RequestDto::getPrice).sum());
        System.out.println("Total price for client '" + clientId + "': " + totalPrice + ".");
        return totalPrice;
    }

    /**
     * Retrieves all requests from the database and prints them out.
     *
     * @return list of all requests
     */
    private List<RequestDto> generateRequestList() {
        List<RequestDto> requests = requestService.findAll();
        System.out.println("All requests:");
        requests.forEach(System.out::println);
        return requests;
    }

    /**
     * Retrieves requests from the database for specified client and prints them out.
     *
     * @param clientId clientId to filter by
     * @return list of requests for specified client
     * @throws ClientNotFoundException when no requests are found for specified client
     */
    private List<RequestDto> generateRequestListForClient(final String clientId)
            throws ClientNotFoundException {
        List<RequestDto> requests = requestService.findByClientId(clientId);
        System.out.println("Requests for client '" + clientId + "':");
        requests.forEach(System.out::println);
        return requests;
    }

    /**
     * Retrieves all requests from the database and prints theirs average price.
     *
     * @return the average price for all requests
     */
    private String calculateAvgPrice() {
        String avgPrice = numberFormat.format(requestService.findAll().stream()
                .mapToDouble(RequestDto::getPrice).average().orElse(Double.NaN));
        System.out.println("Average price: " + avgPrice + ".");
        return avgPrice;
    }

    /**
     * Retrieves requests from the database for specified client and prints theirs average price.
     *
     * @param clientId clientId to filter by
     * @return the average price of requests for specified client
     * @throws ClientNotFoundException when no requests are found for specified client
     */
    private String calculateAvgPriceForClient(final String clientId)
            throws ClientNotFoundException {
        String avgPrice = numberFormat.format(requestService.findByClientId(clientId).stream()
                .mapToDouble(RequestDto::getPrice).average().orElse(Double.NaN));
        System.out.println("Average price for client '" + clientId + "': " + avgPrice + ".");
        return avgPrice;
    }

    /**
     * Generates the csv report.
     *
     * @param content  content to save - objects of the following type(s) are allowed
     *                 {@link List<RequestDto> }
     *                 {@link String }
     * @param fileName initial name of the report file
     * @param scanner  reads user's input to determine whether the report should be generated
     */
    private void generateCsvReport(final Object content, final String fileName, final Scanner scanner) {
        System.out.println("\nWould you like to export this to a csv file? Type 'yes' to generate the report.");
        String report = scanner.next();
        switch (report.toLowerCase()) {
            case "yes":
                System.out.println("Generating report...");
                csvWriter.generateCsvReport(content, fileName);
                System.out.println("Report successfully saved in location './reports'!");
                break;
            case "no":
                System.out.println("\nReturning to main menu...");
                break;
            default:
                System.err.println("\nUnrecognized command, returning to main menu...");
        }
    }

    /**
     * Saves received list of requests into the database.
     *
     * @param requests list of requests to be saved
     */
    private void saveRequests(final List<RequestDto> requests) {
        requests.forEach(requestService::save);
    }

    /**
     * Finds csv files in received list of files.
     * <p>
     * Runs through received list of files and determines whether each file is an csv file.
     * If so, the file is added to the returned list.
     * </p>
     *
     * @param files list of files to process
     * @return list of csv files
     */
    private List<File> findCsvFiles(final List<File> files) {
        return separateFiles(files, "csv");
    }

    /**
     * Finds xml files in received list of files.
     * <p>
     * Runs through received list of files and determines whether each file is an xnl file.
     * If so, the file is added to the returned list.
     * </p>
     *
     * @param files list of files to process
     * @return list of xml files
     */
    private List<File> findXmlFiles(final List<File> files) {
        return separateFiles(files, "xml");
    }

    /**
     * Separates list of files based on provided file extension.
     *
     * @param files         list of files to separate
     * @param fileExtension specified extension
     * @return list of separated files
     */
    private List<File> separateFiles(final List<File> files, final String fileExtension) {
        return files.stream()
                .filter(f -> f.getName().toLowerCase().endsWith(fileExtension))
                .collect(Collectors.toList());
    }

    /**
     * Gets user's input of clientId.
     *
     * @param scanner scanner that is responsible for reading input for clientId
     * @return user's input
     */
    private String readClientId(final Scanner scanner) {
        System.out.print("Enter client id: ");
        return scanner.next();
    }
}
