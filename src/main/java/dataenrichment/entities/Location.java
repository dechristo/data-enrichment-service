package dataenrichment.entities;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "country")
    @NotBlank(message = "Country must not be empty.")
    private String country;

    @Column(name = "city")
    @NotBlank(message = "City must not be empty.")
    private String city;

    @Column(name = "sensor_name")
    @NotBlank(message = "Sensor name must not be empty.")
    private String sensorName;

    public Location() {

    }

    public Location(String country, String city, String sensorName) {
        this.country = country;
        this.city = city;
        this.sensorName = sensorName;
    }

    public Location(long id, String country, String city, String sensorName) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.sensorName = sensorName;
    }

    public long getId() {
        return id;
    }

    public String getCountry() {
        return country;
    }

    public String getCity() {
        return city;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }
}
