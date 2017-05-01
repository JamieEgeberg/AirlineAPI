package entity;

/**
 * Created by Niki on 2017-05-01.
 *
 * @author Niki
 */
public class Flight {

    public String flightID;
    public String flightNumber;
    public String date; // ISO-8601 date+time
    public int numberOfSeats;
    public Number totalPrice;
    public int traveltime;
    public String origin; // IATA-Code
    public String destination; // IATA-Code

}
