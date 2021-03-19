package dataenrichment.models;

import common.TemperatureSensor;

public class EnrichedTemperatureSensor extends TemperatureSensor {
    Location location;

    public EnrichedTemperatureSensor(Location location) {
        this.location = location;
    }

    public EnrichedTemperatureSensor(String name, double value, Location location) {
        super(name, value);
        this.location = location;
    }

    public Location getLocation() {
        return location;
    }
}
