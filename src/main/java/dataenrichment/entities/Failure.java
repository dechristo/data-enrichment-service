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
    private float errorValue;

    @Column(name = "times")
    private long times;

    public Failure(long id, String sensorName, float errorValue, long times) {
        this.id = id;
        this.sensorName = sensorName;
        this.errorValue = errorValue;
        this.times = times;
    }

    public long getId() {
        return id;
    }

    public String getSensorName() {
        return sensorName;
    }

    public float getErrorValue() {
        return errorValue;
    }

    public long getTimes() {
        return times;
    }
}
