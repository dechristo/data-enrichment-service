# Data Enrichment Service

This service consumes temperature data values.
It also exposes 3 endpoints described in section 4.

## 1. Dependencies
The service need an instance of pulsar running in your system.
https://pulsar.apache.org/docs/en/standalone/

## 2. Build
The service was built using Gradle. You can build it with your
favorite IDE or via console (linux or OSx) with  
`./gradlew build`

## 3. Start
You can  start the application with your IDE or in the console with 
`java -jar build/libs/data-enrichment-service1.0-SNAPSHOT.jar`.

## 4. Endpoints
The service runs on `http://localhost:3010`

### 4.1 `GET /locations`
Returns a JSON response with location data.

### 4.2 `POST /location`
Creates a location.

Body: 
Header: `Content-Type: application/json`
```
{
	"city": "Stochholm",
	"country": "Sweden",
	"sensorName": "freeze-sensor-202"
}
```


### 4.3 `GET /failures`
Returns a JSON response with failure data.

### 4.4 `GET /enriched-failures`
Returns a JSON response with enriched failure data.
