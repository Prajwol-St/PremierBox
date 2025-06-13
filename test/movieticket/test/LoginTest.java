/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package movieticket.test;

/**
 *
 * @author acer
 */
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
public class LoginTest {
    private Login login;
    
    @Before
    public void setup(){
        login = new Login();
        login.setUser("user1","password123");   
    }
    
    @Test
    public void testLoginSuccess(){
        assertTrue(login.login("user1","password123"));
    }
    @Test
    public void testLoginWrongPassword(){
        assertFalse(login.login("user1","wrongpass"));
    }
    @Test
    public void testLoginUnknownUser(){
        assertFalse(login.login("unkonwn","password123"));
    }
}

class Login {
    private String storedUsername = null;
    private String storedPassword  = null;
    
    public void setUser(String username, String password){
        storedUsername = username;
        storedPassword = password;
    }
    public boolean login(String username, String password){
        if(storedUsername == null)return false;
        return storedUsername.equals(username)&& storedPassword.equals(password);
    }
}
