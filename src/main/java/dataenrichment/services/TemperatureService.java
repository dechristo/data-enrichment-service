package dataenrichment.services;
import com.fasterxml.jackson.databind.ObjectMapper;
import common.TemperatureSensor;
import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.MessageListener;
import org.apache.pulsar.client.api.PulsarClient;
import org.apache.pulsar.client.impl.schema.JSONSchema;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ObjectInput;
import java.io.ObjectInputStream;


public class TemperatureService {
    private Logger logger;

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
                        logger.info("Sensor name" + data.getName());
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
}


