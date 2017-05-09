package entity;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 * Created by Niki on 2017-05-01.
 *
 * @author Niki
 */
@Entity(name = "FLIGHT")
public class Flight implements Serializable {

    @Id
    @Expose
    String flightID;
    @Expose
    String flightNumber;
    @Expose
    String date; // ISO-8601 date+time
    @Expose
    int numberOfSeats;
    @Expose
    Number totalPrice;
    @Expose
    int traveltime;
    @Expose
    String origin; // IATA-Code
    @Expose
    String destination; // IATA-Code
    
    @Expose(serialize = false)
    @ManyToOne
    IATA originIATA;
    @Expose(serialize = false)
    @ManyToOne
    IATA destinationIATA;

    public String getFlightID() {
        return flightID;
    }

    public void setFlightID(String flightID) {
        this.flightID = flightID;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getNumberOfSeats() {
        return numberOfSeats;
    }

    public void setNumberOfSeats(int numberOfSeats) {
        this.numberOfSeats = numberOfSeats;
    }

    public Number getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Number totalPrice) {
        this.totalPrice = totalPrice;
    }

    public int getTraveltime() {
        return traveltime;
    }

    public void setTraveltime(int traveltime) {
        this.traveltime = traveltime;
    }

    public String getOrigin() {
        return origin;
    }

    public void setOrigin(String origin) {
        this.origin = origin;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

}
