/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

import com.google.gson.annotations.Expose;
import java.util.List;
import javax.persistence.OneToMany;

/**
 *
 * @author Jamie
 */
public class ReservationResponse {

    public ReservationResponse() {
    }

    public ReservationResponse(Flight flight, Reservation res) {
        flightNumber = flight.getFlightNumber();
        origin = flight.getOrigin();
        destination = flight.getDestination();
        date = flight.getDate();
        numberOfSeats = res.getNumberOfSeats();
        flightTime = flight.getTraveltime();
        reserveeName = res.getReserveeName();
        passengers = res.getPassengers();
    }

    @Expose
    String flightNumber;

    @Expose
    String origin; // IATA-Code

    @Expose
    String destination; // IATA-Code

    @Expose
    String date; // ISO-8601 date+time

    @Expose
    int numberOfSeats;

    @Expose
    int flightTime;

    String reserveeName;

    @OneToMany
    List<Passenger> passengers;

}
