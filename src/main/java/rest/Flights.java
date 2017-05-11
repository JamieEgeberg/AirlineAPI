package rest;

import com.google.gson.Gson;
import entity.Airline;
import entity.Flight;
import facades.FlightFacade;
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

    @GET
    @Path("{from}/{date}/{tickets}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFlightsFromDateTickets(@PathParam("from") String from,
                                            @PathParam("date") String date,
                                            @PathParam("tickets") int tickets) {
        List<Flight> flights = facade.getFlightsByOriginAndDate(from, date);
        if (flights != null && flights.size() > 0) {
            for (int i = 0; i < flights.size(); i++)
                flights.get(i).setNumberOfSeats(tickets);
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
                                            @PathParam("tickets") int tickets) {
        List<Flight> flights = facade.getFlightsByOriginAndDestinationAndDate
                (from, to, date);
        if (flights != null && flights.size() > 0) {
            for (int i = 0; i < flights.size(); i++)
                flights.get(i).setNumberOfSeats(tickets);
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
