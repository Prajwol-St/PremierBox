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


public class RegistrationTest {
    private Registration registration;
    
    @Before
    public void setUp(){
        registration = new Registration();
    }
    
    @Test
    public void testRegisterSuccess(){
        assertTrue(registration.register("user1","password123","user@example.com"));
    }
    @Test
    public void testUserDuplicate(){
        registration.register("user1","pass123","user1@example.com");
        assertFalse(registration.register("user1","new123","newuser@example.com"));
    }
    
}
class Registration {
    private String storedUsername = null;
     private String storedPassword = null;
    private String storedEmail = null;

    public boolean register(String username, String password, String email) {
        // Check if user already exists
        if (storedUsername != null && storedUsername.equals(username)) {
            return false; // User already registered
        }
        if (username == null || username.isEmpty()) return false;
        if (password == null || password.length() < 6) return false;
        if (email == null || !email.matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) return false;

        // Store user info
        storedUsername = username;
        storedPassword = password;
        storedEmail = email;
        return true;
    }
}


