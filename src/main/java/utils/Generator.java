package utils;

import entity.Airline;
import entity.Flight;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Random;
import java.util.TimeZone;

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
            flight.destination = getIATACode(from);
            flight.date = getADate(date);
            flight.numberOfSeats = tickets;
            flight.origin = from;
            flight.traveltime = bound + 90;
            flight.totalPrice = tickets * (bound + 40);
            flight.flightID = fNumber + "-" +
                    random.nextInt(9999999) + 9999999;
            flight.flightNumber = from + "-" + flight.destination +
                    "-" + fNumber;
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
            flight.destination = to;
            flight.date = getADate(date);
            flight.numberOfSeats = tickets;
            flight.origin = from;
            flight.traveltime = bound + 90;
            flight.totalPrice = tickets * (bound + 40);
            flight.flightID = fNumber + "-" +
                    random.nextInt(9999999) + 9999999;
            flight.flightNumber = from + "-" + flight.destination +
                    "-" + fNumber;
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
        int[] dateInIntegers = new int[3];
        String[] dateInStrings = date.split("-");
        for (int i = 0; i < dateInStrings.length && i < dateInIntegers.length;
             i++) {
            dateInIntegers[i] = Integer.parseInt(dateInStrings[i]);
        }
        Calendar calendar = new GregorianCalendar(dateInIntegers[0],
                                                  dateInIntegers[1],
                                                  dateInIntegers[2],
                                                  random.nextInt(24),
                                                  random.nextInt(60));
        TimeZone tz = TimeZone.getTimeZone("UTC");
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm'Z'");
        // Quoted "Z" to indicate UTC, no timezone offset
        df.setTimeZone(tz);
        return df.format(calendar.getTime());
    }

}
