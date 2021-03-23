package dataenrichment.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.TemperatureSensor;
import dataenrichment.entities.Failure;
import dataenrichment.models.EnrichedTemperatureDataDTO;
import dataenrichment.repository.FailureRepository;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class TemperatureMonitorService {
    private Logger logger;
    private static final String PULSAR_URL = "pulsar://localhost:6650";
    private static final String TOPIC_NAME = "temperature-sensor-event";
    private static final String SUBSCRIPTION_NAME = "data-enrichment-service-temperature";

    @Autowired
    private FailureRepository failureRepository;

    @Autowired
    private DataEnrichmentService dataEnrichmentService;

    @Autowired
    private MailService mailService;

    public void initPulsar() {
        logger = LoggerFactory.getLogger(TemperatureMonitorService.class);
        PulsarClient client;
        Consumer<TemperatureSensor> pulsarConsumer;

        try {
            client = PulsarClient.builder()
                .serviceUrl(PULSAR_URL)
                .build();

            pulsarConsumer = client.newConsumer(JSONSchema.of(TemperatureSensor.class))
                .topic(TOPIC_NAME)
                .subscriptionName(SUBSCRIPTION_NAME)
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
            logger.error("Error consuming message: " + ex.getMessage());
        }
    }

    private void handleTemperatureAlert(TemperatureSensor data) {
        Failure failure = null;
        if (data.getValue() >= 34) {
            logger.warn(String.format("Critical temperature event at sensor %s :  %s", data.getName(), data.getValue()));
            failure = new Failure(data.getName(), data.getValue());
            this.saveFailure(failure);
            this.notifyByEmail(failure);
        }
    }

    private void saveFailure(Failure failure) {
        try {
            failureRepository.save(failure);
            logger.info(String.format("Critical failure for sensor %s saved.", failure.getSensorName()));
        }
        catch (Exception ex) {
            logger.error("Failed to save sensor error data into database: " + ex.getMessage());
        }
    }

    private void notifyByEmail(Failure failure) {
        EnrichedTemperatureDataDTO enrichedTemperatureDataDTO = dataEnrichmentService.enrich(failure);
        try {
            mailService.sendEmail(enrichedTemperatureDataDTO);
            logger.info("Email sent.");
        }
        catch (Exception ex) {
            logger.error("Failed to send sensor error data email: " + ex.getMessage());
        }
    }
}


