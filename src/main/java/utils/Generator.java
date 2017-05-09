package utils;

import entity.Airline;
import entity.Flight;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Niki on 2017-05-01.
 *
 * @author Niki
 */
public class Generator {

    private static Random random = new Random();

    private static String[] IATACodes = {"CPH", "ATL", "PEK", "ORD", "LHR",
            "HND", "LAX", "CDG", "DFW", "FRA", "DEN", "HKG", "MAD", "DXB",
            "JFK", "AMS", "CGK", "BKK", "SIN", "CAN", "PVG", "IAH", "LAS",
            "SFO", "PHX", "CLT", "FCO", "SYD", "MIA", "MCO", "MUC"};

    public Airline generateFlights(String from,
                                   String date,
                                   int tickets) {
        Airline airline = new Airline();
        int count = random.nextInt(9) + 2;
        for (int i = 0; i < count; i++) {
            int bound = random.nextInt(400);
            int fNumber = random.nextInt(8999) + 1001;
            Flight flight = new Flight();
            flight.setDestination(getIATACode(from));
            flight.setDate( getADate(date));
            flight.setNumberOfSeats(tickets); 
            flight.setOrigin(from); 
            flight.setTraveltime(bound + 90); 
            flight.setTotalPrice( tickets * (bound + 40));
            flight.setFlightID("3333-" +
                    (random.nextInt(9999999) + 9999999));
            flight.setFlightNumber(from + "-" + flight.getDestination() +
                    "-" + fNumber);
            airline.flights.add(flight);
        }
        return airline;
    }

    public Airline generateFlights(String from,
                                   String to,
                                   String date,
                                   int tickets) {
        Airline airline = new Airline();
        int count = random.nextInt(9) + 2;
        for (int i = 0; i < count; i++) {
            int bound = random.nextInt(400);
            int fNumber = random.nextInt(8999) + 1001;
            Flight flight = new Flight();
            flight.setDestination(to);
            flight.setDate( getADate(date));
            flight.setNumberOfSeats(tickets); 
            flight.setOrigin(from); 
            flight.setTraveltime(bound + 90); 
            flight.setTotalPrice( tickets * (bound + 40));
            flight.setFlightID(fNumber + "-" +
                    (random.nextInt(9999999) + 9999999));
            flight.setFlightNumber(from + "-" + flight.getDestination() +
                    "-" + fNumber);
            airline.flights.add(flight);
        }
        return airline;
    }

    private String getIATACode(String from) {
        String destination;
        do {
            destination = IATACodes[random.nextInt(IATACodes.length)];
        } while (destination.equals(from));
        return destination;
    }

    private String getADate(String date) {
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        try {
            Date newDate = df.parse(date);
            Calendar calendar = new GregorianCalendar();
            calendar.setTime(newDate);
            calendar.set(Calendar.HOUR, random.nextInt(24));
            calendar.set(Calendar.MINUTE, random.nextInt(60));
            df.setTimeZone(tz);
            return df.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
            return date;
        }
    }

}
