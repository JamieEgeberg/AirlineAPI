package test;

import entity.Flight;
import entity.PU;
import facades.FlightFacade;
import java.util.List;
import javax.persistence.Persistence;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import security.IUser;
import utils.Generator;

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
    }

    @Test
    public void testGetFlightByFlightId() {
        testFlight = Generator.generateFlights("CPH", "CDG", 1).flights.get(0);
        Flight flight = facade.getFlightByFlightId(testFlight.getFlightID());
        assertEquals(testFlight.getFlightID(), flight.getFlightID());
        assertEquals(testFlight.getFlightNumber(), flight.getFlightNumber());
    }

    @Test
    public void testAddFlight() {
        testFlight = Generator.generateFlights("CPH", "DEN", 1).flights.get(0);
        facade.addFlight(testFlight);
        //Verify that user was actually inserted in the database
        Flight flight = facade.getFlightByFlightId(testFlight.getFlightID());

        assertEquals(testFlight.getFlightID(), flight.getFlightID());
        assertEquals(testFlight.getFlightNumber(), flight.getFlightNumber());
    }

}
