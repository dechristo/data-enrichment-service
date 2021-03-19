package dataenrichment.entities;

import javax.persistence.*;

@Entity
@Table(name = "failure")
public class Failure {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "sensor_name")
    private String sensorName;

    @Column(name = "error_value")
    private double errorValue;

    public Failure() {}

    public Failure(long id, String sensorName, double errorValue) {
        this.id = id;
        this.sensorName = sensorName;
        this.errorValue = errorValue;
    }

    public Failure(String sensorName, double errorValue) {
        this.sensorName = sensorName;
        this.errorValue = errorValue;
    }

    public long getId() {
        return id;
    }

    public String getSensorName() {
        return sensorName;
    }

    public double getErrorValue() {
        return errorValue;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public void setErrorValue(double errorValue) {
        this.errorValue = errorValue;
    }
}
