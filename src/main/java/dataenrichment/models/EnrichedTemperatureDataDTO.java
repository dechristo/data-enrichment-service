package dataenrichment.models;

import common.TemperatureSensor;

public class EnrichedTemperatureDataDTO extends TemperatureSensor {
    LocationDTO locationDTO;

    public EnrichedTemperatureDataDTO(LocationDTO locationDTO) {
        this.locationDTO = locationDTO;
    }

    public EnrichedTemperatureDataDTO(String name, double value, LocationDTO locationDTO) {
        super(name, value);
        this.locationDTO = locationDTO;
    }

    public LocationDTO getLocation() {
        return locationDTO;
    }
}
