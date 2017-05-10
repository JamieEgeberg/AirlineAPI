/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facades;

import entity.Flight;
import entity.User;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.TypedQuery;
import security.IUser;
import security.PasswordStorage;

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

    public IUser getFlightByFlightId(String id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(User.class, id);
        } finally {
            em.close();
        }
    }

    public List<Flight> getFlightsByOriginAndDate(String origin, String date) {
        EntityManager em = getEntityManager();
        TypedQuery<Flight> query = em.createQuery("SELECT f FROM FLIGHT f WHERE f.origin=:org AND f.date LIKE :date",Flight.class);
        query.setParameter("org", origin);
        query.setParameter("date", date.toLowerCase().split("t")[0]);
        return query.getResultList();
    }

    /*
  Return the Roles if users could be authenticated, otherwise null
     */
    public List<String> authenticateUser(String userName, String password) {
        IUser user = getFlightByFlightId(userName);
        if (user == null) {
            return null;
        }
        boolean passwordOK = false;
        try {
            passwordOK = PasswordStorage.verifyPassword(password, user.getPassword());
        } catch (PasswordStorage.CannotPerformOperationException | PasswordStorage.InvalidHashException ex) {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user != null && passwordOK ? user.getRolesAsStrings() : null;
    }

    public IUser addUser(String userName, String password) {
        EntityManager em = getEntityManager();
        try {
            User user = new User(userName, password);
            em.getTransaction().begin();
            em.persist(user);
            em.getTransaction().commit();
            return user;

        } catch (PasswordStorage.CannotPerformOperationException ex) {
            Logger.getLogger(UserFacade.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        } finally {
            em.close();
        }
    }

}
