package dataenrichment;

import dataenrichment.services.TemperatureService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class App {
    public static void main(String... args) {
        SpringApplication.run(App.class, args);
        TemperatureService temperatureService = new TemperatureService();
        temperatureService.initPulsar();
    }
}
