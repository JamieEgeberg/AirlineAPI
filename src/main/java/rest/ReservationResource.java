/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

        import com.google.gson.Gson;
        import com.google.gson.GsonBuilder;
        import entity.Flight;
        import entity.Reservation;
        import entity.ReservationResponse;
        import facades.FlightFacade;
        import javax.persistence.EntityManagerFactory;
        import javax.persistence.Persistence;
        import javax.ws.rs.*;
        import javax.ws.rs.core.Context;
        import javax.ws.rs.core.UriInfo;
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
    private static GsonBuilder builder;
    private static Gson gsonOut;

    /**
     * Creates a new instance of ReservationResource
     */
    public ReservationResource() {
        builder = new GsonBuilder();
        builder.excludeFieldsWithoutExposeAnnotation();
        gsonOut = builder.create();
        emf = Persistence.createEntityManagerFactory("pu_development");
        facade = new FlightFacade(emf);
    }

    @POST
    @Path("{flightId}")
    @Produces(MediaType.APPLICATION_JSON)
    public String postFlightsReservation(@PathParam("flightId") String flightId, String json) {
        Reservation res = gson.fromJson(json, Reservation.class);
        Flight flight = facade.getFlightByFlightId(flightId);
        facade.addReservation(res);
        return gsonOut.toJson(new ReservationResponse(flight, res));
    }
}
