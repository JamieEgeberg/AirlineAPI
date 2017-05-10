package test;

import entity.Flight;
import entity.PU;
import entity.Passenger;
import entity.Reservation;
import facades.FlightFacade;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Persistence;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import utils.Generator;
import utils.util;

public class FlightFacadeTest {

    FlightFacade facade;
    Flight testFlight;

    //Override this in a derived class to use an alternative database
    public void setPersistenceUnit() {
        PU.setPU_Name("pu_memorydb_mock");
    }

    @Before
    public void initFacadeAndTestUsers() {
        setPersistenceUnit();
        facade = new FlightFacade(Persistence.createEntityManagerFactory(PU.getPersistenceUnitName()));
        //Setup test users
        utils.makeTestUsers.main(null);

        testFlight = Generator.generateFlights("CPH", "CDG", util.getToday(), 1).flights.get(0);
        facade.addFlight(testFlight);
    }

    @Test
    public void testGetFlightByFlightId() {
        Flight flight = facade.getFlightByFlightId(testFlight.getFlightID());
        assertEquals(testFlight.getFlightID(), flight.getFlightID());
        assertEquals(testFlight.getFlightNumber(), flight.getFlightNumber());
    }

    @Test
    public void testAddFlight() {
        Flight flightToAdd = Generator.generateFlights("CPH", "DEN", util.getToday(), 1).flights.get(0);
        facade.addFlight(flightToAdd);
        //Verify that user was actually inserted in the database
        Flight addedFlight = facade.getFlightByFlightId(flightToAdd.getFlightID());

        assertEquals(flightToAdd.getFlightID(), addedFlight.getFlightID());
        assertEquals(flightToAdd.getFlightNumber(), addedFlight.getFlightNumber());
    }

    @Test
    public void testAddReservation() {
        List<Passenger> passengers = new ArrayList<>();
        passengers.add(new Passenger("Test", "Testsen"));
        Reservation testRes = new Reservation("3333-123456789", passengers.size(), "Test Testsen", "42424242", "test@testsen.dk", passengers);
        facade.addReservation(testRes);
        //Verify that user was actually inserted in the database
        Reservation res = facade.getReservationBytId(testRes.getId());

        assertEquals(testRes.getFlightID(), res.getFlightID());
        assertEquals(testRes.getReversePhone(), res.getReversePhone());
    }

}
