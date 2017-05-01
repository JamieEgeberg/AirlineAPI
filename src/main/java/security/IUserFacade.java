package security;

import java.util.List;

/**
 *
 * @author lam
 */
public interface IUserFacade {

    /*
    Return the Roles if users could be authenticated, otherwise null
     */
    List<String> authenticateUser(String userName, String password);

    /**
     * Get a user by id (userName)
     * @param id The username
     * @return the user or null if not found
     */
    IUser getUserByUserId(String id);
    
    /**
     * Add a new user, given userName and passwor
     * @param userName 
     * @param password
     * @return The new user, with the hashed password
     */
    IUser addUser(String userName, String password);
    
}
