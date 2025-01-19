package at.technikumwien.workerservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class MainWorkerApp {
    public static void main(String[] args) {
        SpringApplication.run(MainWorkerApp.class, args);
    }
}
