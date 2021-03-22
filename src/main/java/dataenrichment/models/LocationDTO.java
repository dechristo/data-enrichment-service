package dataenrichment.models;

public class LocationDTO {
    private String city;
    private String country;

    public LocationDTO() {}

    public LocationDTO(String city, String country) {
        this.city = city;
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public String getCountry() {
        return country;
    }
}
