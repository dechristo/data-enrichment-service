package dataenrichment.entities;

import javax.persistence.*;

@Entity
@Table(name = "location")
public class Location {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "sensor_name")
    private String sensorName;

    public Location() {

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

    public void setId(long id) {
        this.id = id;
    }
}
