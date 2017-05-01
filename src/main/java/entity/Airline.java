package entity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niki on 2017-05-01.
 *
 * @author Niki
 */
public class Airline {

    public String airline = "JENS air";
    public List<Flight> flights;

    public Airline() {
        flights = new ArrayList<>();
    }

}
