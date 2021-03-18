package common;

import java.io.Serializable;
import java.util.Date;

public class TemperatureSensor implements Serializable {
    private String name;
    private double value;
    private long datetime;

    public TemperatureSensor() {}

    public TemperatureSensor(String name, double value) {
        this.name = name;
        this.value = value;
        this.datetime = (new Date()).getTime();
    }

    public String getName() {
        return name;
    }

    public double getValue() {
        return value;
    }

    public long getDatetime() {
        return datetime;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public void setDatetime(long datetime) {
        this.datetime = datetime;
    }
}
