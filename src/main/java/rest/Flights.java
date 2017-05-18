package rest;

import com.google.gson.Gson;
import entity.Airline;
import entity.Flight;
import facades.FlightFacade;
import httpErrors.AirlineException;
import static java.lang.Integer.parseInt;
import utils.Generator;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;

/**
 * Created by Niki on 2017-05-01.
 *
 * @author Niki
 */
@Path("flights")
public class Flights {
    
    private EntityManagerFactory emf;
    private FlightFacade facade;
    
    private static Gson gson = new Gson();
    
    @Context
    private UriInfo context;
    
    public Flights() {
        emf = Persistence.createEntityManagerFactory("pu_development");
        facade = new FlightFacade(emf);
    }
    
    private void inputValidation(String from, String date, int tickets) throws AirlineException {
        inputValidation(from, "XXX", date, tickets);
    }

    private void inputValidation(String from, String to, String date, int tickets) throws AirlineException {
        if (from.equals(to)) {
            throw new AirlineException(3, 400, "You can't fly to the same place you are starting at!: " + from + "=" + to);
        }
        if (date.length() != 24) {
            throw new AirlineException(3, 400, "You goofed your date formatting! Please follow the ISO standard: " + date);
        }
        if (from.length() != 3) {
            throw new AirlineException(3, 400, "You goofed your starting airport! IATA codes consist of 3 capital letters!: " + from);
        }
        if (!to.equals("XXX") && to.length() != 3) {
            throw new AirlineException(3, 400, "You goofed your destination airport! IATA codes consist of 3 capital letters!: " + to);
        }
        if (tickets <=0 || tickets >=300) {
            throw new AirlineException(3, 400, "You goofed your tickets!  Should be between 1 and 300: " + tickets);
        }
        
    }
    
    @GET
    @Path("{from}/{date}/{tickets}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFlightsFromDateTickets(@PathParam("from") String from,
            @PathParam("date") String date,
            @PathParam("tickets") int tickets) throws AirlineException {
        inputValidation(from, date, tickets);
        List<Flight> flights = facade.getFlightsByOriginAndDate(from, date);
        if (flights != null && flights.size() > 0) {
            for (int i = 0; i < flights.size(); i++) {
                flights.get(i).setNumberOfSeats(tickets);
            }
            Airline airline = new Airline();
            airline.airline = "JENS air";
            airline.flights = flights;
            return gson.toJson(airline);
        } else {
            Airline airline = Generator.generateFlights(from, date, tickets);
            for (int i = 0; i < airline.flights.size(); i++) {
                facade.addFlight(airline.flights.get(i));
            }
            return gson.toJson(airline);
        }
        
    }
    
    @GET
    @Path("{from}/{to}/{date}/{tickets}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFlightsFromDateTickets(@PathParam("from") String from,
            @PathParam("to") String to,
            @PathParam("date") String date,
            @PathParam("tickets") int tickets) throws AirlineException {
        inputValidation(from, to, date, tickets);
        List<Flight> flights = facade.getFlightsByOriginAndDestinationAndDate(from, to, date);
        if (flights != null && flights.size() > 0) {
            for (int i = 0; i < flights.size(); i++) {
                flights.get(i).setNumberOfSeats(tickets);
            }
            Airline airline = new Airline();
            airline.airline = "JENS air";
            airline.flights = flights;
            return gson.toJson(airline);
        } else {
            Airline airline = Generator.generateFlights(from,
                    to,
                    date,
                    tickets);
            for (int i = 0; i < airline.flights.size(); i++) {
                facade.addFlight(airline.flights.get(i));
            }
            return gson.toJson(airline);
        }
        
    }
    
}
