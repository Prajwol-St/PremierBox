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
import java.util.Random;
public class ResetPasswordTest {
    
    private ResetPassword reserPassword;
    
    @Before 
    public void setUp(){
        resetPassword = new ResetPassword();
        resetPassword.setUser("user1","oldPass123");
        
    }
    @Test
    public void testGenerateOtp(){
        String otp = resetPassword.generatedOtp("user1");
        assertNotNull(otp);
        assertEquals(6, otp.length());
    }
}

class ResetPassword{
    private String storedUsername = null;
    private String storedPassword = null;
    private String currentOtp = null;
    private Random random = new Random();
    
    public boolean resetPassword(String username, String otp, String newPassword){
        if (storedUsername == null || !storedUsername.equals(username))return false;
         if (currentOtp == null || !currentOtp.equals(otp)) return false;
        if (newPassword == null || newPassword.length() < 6) return false;

        storedPassword = newPassword;
        currentOtp = null;  // clear OTP after reset
        return true;
    }
}
