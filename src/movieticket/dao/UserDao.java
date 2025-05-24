/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package movieticket.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import movieticket.database.MySqlConnection;
import movieticket.model.LoginRequest;
import movieticket.model.UserData;

/**
 *
 * @author Hp
 */
public class UserDao {
     MySqlConnection mySql = new MySqlConnection();
    public boolean registerUser(UserData userData){
        Connection conn= mySql.openConnection();
         String createTableSQL = "CREATE TABLE IF NOT EXISTS demoUserss ("
            + "id INT AUTO_INCREMENT PRIMARY KEY, "               
            + "name VARCHAR(50) NOT NULL, "
            + "email VARCHAR(100) UNIQUE NOT NULL, "
            + "password VARCHAR(255) NOT NULL, "
            + "image BLOB NOT NULL"
            + ")";
         String query=  "INSERT INTO demoUserss (name, email, password,image) VALUES (?, ?, ?,?)";
         
        try {
            PreparedStatement createtbl= conn.prepareStatement(createTableSQL);
            createtbl.executeUpdate();
        } catch (SQLException ex) {
            java.util.logging.Logger.getLogger(UserDao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        
        try (PreparedStatement pstmt = conn.prepareStatement(query)) {
            pstmt.setString(1, userData.getName());
            pstmt.setString(2, userData.getEmail());
            pstmt.setString(3, userData.getPassword());
            pstmt.setBytes(4, userData.getImage());
            int result = pstmt.executeUpdate();
            return result > 0;
        } catch (SQLException ex) {
            System.err.println(ex);

        } finally {
            mySql.closeConnection(conn);
        }
          return false;
    }
    
    public UserData loginUser(LoginRequest loginData){
        Connection conn = mySql.openConnection();
        String sql = "SELECT * FROM demoUserss where email = ? and password = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, loginData.getEmail());
            pstmt.setString(2, loginData.getPassword());
            ResultSet result = pstmt.executeQuery();
            if(result.next()){
                UserData user  = new UserData(
                    result.getString("name"),
                    result.getString("email"),
                    result.getString("password"),
                    result.getBytes("image")                 
                );
                user.setId(result.getInt("id"));
                
                return user;
            }
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            mySql.closeConnection(conn);
        }
        return null;
    }
}
