/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package movieticket.dao;

import movieticket.model.LoginRequest;
import movieticket.model.ResetPasswordRequest;
import movieticket.model.UserData;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Hp
 */
public class UserDaoTest {
    
    String correctName="Test User3";
    String correctEmail = "testuser3@gmail.com";
    String password = "password123@";
     private  byte[] image;
     
     @Test
     public void registerWithNewDetails(){
        UserData user = new UserData( correctName,  correctEmail, password, image);
        UserDao dao = new UserDao();
        boolean result = dao.registerUser(user);
        Assert.assertTrue("Register should be successfull",result);
    }
    
    @Test
    public void registerWithExistingDetails(){
        UserData user = new UserData( correctName,  correctEmail, password, image);
        UserDao dao = new UserDao();
        boolean result = dao.registerUser(user);
        Assert.assertTrue("Register should be successfull",result);
    }
    
    @Test
    public void loginWithCorrectCredentials(){
        LoginRequest req = new LoginRequest(correctEmail, password);
        UserDao dao = new UserDao();
        UserData user = dao.loginUser(req);
        Assert.assertNotNull("user should be returned ", user);
        Assert.assertEquals("Name should match", correctName, user.getName());
    }
    
    @Test
    public void loginWithWrongCredentials(){
        LoginRequest req = new LoginRequest ("wrong@gmail.com", password);
        UserDao dao = new UserDao();
        UserData user = dao.loginUser(req);
        Assert.assertNull("User should be null", user);
    }
}
