package rest;

import com.google.gson.Gson;
import entity.Airline;
import utils.Generator;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

/**
 * Created by Niki on 2017-05-01.
 *
 * @author Niki
 */
@Path("flights")
public class Flights {

    private static Generator generator = new Generator();
    private static Gson gson = new Gson();

    @Context
    private UriInfo context;

    @GET
    @Path("/{from}/{date}/{tickets}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFlightsFromDateTickets(@PathParam("from") String from,
                                            @PathParam("date") String date,
                                            @PathParam("tickets") int tickets) {
        Airline airline = generator.generateFlights(from, date, tickets);
        return gson.toJson(airline);
    }

    @GET
    @Path("/{from}/{to}/{date}/{tickets}")
    @Produces(MediaType.APPLICATION_JSON)
    public String getFlightsFromDateTickets(@PathParam("from") String from,
                                            @PathParam("to") String to,
                                            @PathParam("date") String date,
                                            @PathParam("tickets") int tickets) {
        Airline airline = generator.generateFlights(from, to, date, tickets);
        return gson.toJson(airline);
    }

}
