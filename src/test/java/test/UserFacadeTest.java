package test;

import entity.PU;
import facades.UserFacade;
import java.util.List;
import javax.persistence.Persistence;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import security.IUser;
import security.IUserFacade;

public class UserFacadeTest {
  
  IUserFacade facade ;
  
  //Override this in a derived class to use an alternative database
  public void setPersistenceUnit() {
    PU.setPU_Name("pu_memorydb_mock");
  }
  
  @Before
  public void initFacadeAndTestUsers(){
    setPersistenceUnit(); 
    facade = new UserFacade(Persistence.createEntityManagerFactory(PU.getPersistenceUnitName()));
    //Setup test users
    utils.makeTestUsers.main(null);
  }
  
  @Test
  public void testGetExsistingUserById() {
    IUser user = facade.getUserByUserId("user");
    System.out.println(user.getPassword());
    assertEquals("user", user.getUserName());
  }
  
  @Test
  public void testGetNonExsistingUserById() {
    IUser user = facade.getUserByUserId("i_dont_exist");
    assertNull(user);
  }

  @Test
  public void testAuthenticateUserOK() {
    List<String> result = facade.authenticateUser("user", "test");
    assertEquals("User should have one role",1, result.size());
  }
  
  @Test
  public void testAuthenticateUserWrongPassword() {
    List<String> result = facade.authenticateUser("user", "wrong password");
    assertNull("Should be null, if user could not login",result);
  }
  
  @Test
  public void testAuthenticateUserDoesNotExist() {
    List<String> result = facade.authenticateUser("iDontExist", "password");
    assertNull("Should be null, if user does not exists",result);
  }
  
  @Test
  public void passwordIsHashedForNewUsers() {
    IUser user = facade.addUser("Kurt", "Secret");
    assertEquals("Kurt", user.getUserName());
    assertTrue("Password should be hashed",user.getPassword().startsWith("sha1:64000:"));
  }
  
  @Test
  public void addUser() {
    facade.addUser("Kurt", "Secret");   
    //Verify that user was actually inserted in the database
    IUser newUser = facade.getUserByUserId("Kurt");
    assertEquals("Kurt", newUser.getUserName());
  }
  
}
