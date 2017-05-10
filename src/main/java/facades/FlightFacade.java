/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Flight;
import entity.Reservation;
import entity.ReservationResponse;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;

/**
 *
 * @author Jamie
 */
public class FlightFacade {

    EntityManagerFactory emf;

    public FlightFacade(EntityManagerFactory emf) {
        this.emf = emf;
    }

    private EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public Flight getFlightByFlightId(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Flight.class, id);
        } finally {
            em.close();
        }
    }

    public List<Flight> getFlightsByOriginAndDate(String origin, String date) {
        EntityManager em = getEntityManager();
        TypedQuery<Flight> query = em.createQuery("SELECT f FROM FLIGHT f WHERE f.origin=:org AND f.date LIKE :date", Flight.class);
        query.setParameter("org", origin);
        query.setParameter("date", date.toLowerCase().split("t")[0]);
        return query.getResultList();
    }

    /*
  Return the Roles if users could be authenticated, otherwise null
     */
    public Flight addFlight(Flight flight) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(flight);
            em.getTransaction().commit();
            return flight;

        } finally {
            em.close();
        }
    }

    public Reservation getReservationBytId(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Reservation.class, id);
        } finally {
            em.close();
        }
    }

    public Reservation addReservation(Reservation reservation) {
        EntityManager em = getEntityManager();
        try {
            em.getTransaction().begin();
            em.persist(reservation);
            em.getTransaction().commit();
            return reservation;

        } finally {
            em.close();
        }
    }

    public ReservationResponse getReservationResponse(Reservation res) {
        Flight flight = getFlightByFlightId(res.getFlightID());
        return new ReservationResponse(flight, res);
    }
}
