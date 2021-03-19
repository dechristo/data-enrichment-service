package dataenrichment.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.TemperatureSensor;
import dataenrichment.entities.Failure;
import dataenrichment.repository.FailureRepository;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TemperatureService {
    private Logger logger;

    @Autowired
    private FailureRepository failureRepository;

    @Autowired
    private MailService mailService;

    public void initPulsar() {
        logger = LoggerFactory.getLogger(TemperatureService.class);
        PulsarClient client;
        Consumer<TemperatureSensor> pulsarConsumer;

        try {
            client = PulsarClient.builder()
                .serviceUrl("pulsar://localhost:6650")
                .build();

            pulsarConsumer = client.newConsumer(JSONSchema.of(TemperatureSensor.class))
                .topic("temperature-sensor-1a")
                .subscriptionName("data-enrichment-service-temperature")
                .messageListener((MessageListener<TemperatureSensor>) (consumer, msg) -> {
                    logger.info("Message received.");

                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        TemperatureSensor data = objectMapper.readValue(msg.getData(), TemperatureSensor.class);
                        handleTemperatureAlert(data);
                        logger.info(String.format("Message received:  sensor [%s] - value [%s]", data.getName(), data.getValue()));
                        consumer.acknowledge(msg);
                    } catch (Exception ex) {
                        logger.error("Error processing temperature sensor data: " + ex.getMessage());
                        consumer.negativeAcknowledge(msg);
                    }
                })
                .subscribe();
        } catch (Exception ex) {
            // log error
        }
    }

    private void handleTemperatureAlert(TemperatureSensor data) {
        if (data.getValue() >= 34) {
            logger.warn(String.format("Critical temperature event at sensor %s :  %s", data.getName(), data.getValue()));
            failureRepository.save(new Failure(data.getName(), data.getValue()));
            mailService.sendEmail();
        }
    }
}


