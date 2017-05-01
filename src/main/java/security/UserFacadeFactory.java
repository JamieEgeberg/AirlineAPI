package security;

import entity.PU;
import facades.UserFacade;
import javax.persistence.Persistence;

/**
 *
 * @author lam
 */
public class UserFacadeFactory {
private static final IUserFacade instance = new UserFacade(Persistence.createEntityManagerFactory(PU.getPersistenceUnitName()));
    public static IUserFacade getInstance(){
        return instance;
    }
}