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
public class TemperatureService {
    private Logger logger;

    @Autowired
    private FailureRepository failureRepository;

    @Autowired
    private DataEnrichmentService dataEnrichmentService;

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
                .topic("temperature-sensor-event")
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
        Failure failure = null;
        if (data.getValue() >= 34) {
            logger.warn(String.format("Critical temperature event at sensor %s :  %s", data.getName(), data.getValue()));
            try {
                failure = new Failure(data.getName(), data.getValue());
                failureRepository.save(failure);
            }
            catch (Exception ex) {
                logger.error("Failed to save sensor error data into database: " + ex.getMessage());
            }

            EnrichedTemperatureDataDTO enrichedTemperatureDataDTO = dataEnrichmentService.enrich(failure);
            try {
                mailService.sendEmail(enrichedTemperatureDataDTO);
            }
            catch (Exception ex) {
                logger.error("Failed to send sensor error data email: " + ex.getMessage());
            }
        }
    }
}


