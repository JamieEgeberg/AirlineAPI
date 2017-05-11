/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import com.google.gson.Gson;
import entity.Flight;
import entity.Reservation;
import entity.ReservationResponse;
import facades.FlightFacade;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * REST Web Service
 *
 * @author Jamie
 */
@Path("reservation")
public class ReservationResource {

    @Context
    private UriInfo context;

    private EntityManagerFactory emf;
    private FlightFacade facade;

    private static Gson gson = new Gson();

    /**
     * Creates a new instance of ReservationResource
     */
    public ReservationResource() {
        emf = Persistence.createEntityManagerFactory("pu_development");
        facade = new FlightFacade(emf);
    }

    @POST
    @Path("/{flightId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String postFlightsReservation(@PathParam("flightId") String flightId, String json) {
        Reservation res = gson.fromJson(json, Reservation.class);
        Flight flight = facade.getFlightByFlightId(flightId);
        facade.addReservation(res);
        return gson.toJson(new ReservationResponse(flight, res));

    }
}
