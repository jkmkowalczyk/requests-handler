package jkmkowalczyk.coreservicestask;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Main application class.
 */
@SpringBootApplication
public class CoreservicesTaskApplication implements CommandLineRunner {
    private final Facade facade;
    private final ConfigurableApplicationContext configurableApplicationContext;

    /**
     * Main constructor for application.
     *
     * @param facade                         application facade
     * @param configurableApplicationContext application context
     */
    public CoreservicesTaskApplication(
            final Facade facade, final ConfigurableApplicationContext configurableApplicationContext) {
        this.facade = facade;
        this.configurableApplicationContext = configurableApplicationContext;
    }

    /**
     * Main method.
     *
     * @param args arguments for running the application
     */
    public static void main(final String[] args) {
        SpringApplication.run(CoreservicesTaskApplication.class, args);
    }

    /**
     * This method runs the application.
     * <p>
     * Firstly, creates List of files based on arguments provided for the application and then displays main menu.
     * </p>
     *
     * @param paths paths for files with requests
     */
    @Override
    public final void run(final String... paths) {
        List<File> fileList = new ArrayList<>();
        for (String path : paths) {
            fileList.add(new File(path));
        }
        facade.processFiles(fileList);
        facade.showMenu();
        configurableApplicationContext.close();
    }
}
