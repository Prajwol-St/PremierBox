/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package movieticket.model;

/**
 *
 * @author Hp
 */
public class UserData {
     private int id;
    private String name;
    private String email;
    private String password;
    private  byte[] image;
    private boolean isAdmin;
    public UserData(String name,String email, String password, byte[] image){
        this.name= name;
        this.email=email;
        this.password=password;
        this.image=image;
    }
    public UserData(int id,String name,String email, String password, byte[] image){
        this.id=id;
        this.name= name;
        this.email=email;
        this.password=password;
        this.image=image;
    }
    public UserData(){
        
    }
    public void setId(int id){
        this.id=id;
    }
    public int getId(){
        return this.id;
    }
    public void setName(String name){
        this.name=name;
    }
    public String getName(){
        return this.name;
    }
    public void setEmail(String email){
        this.email=email;
    }
    public String getEmail(){
        return this.email;
    }
    public void setPassword(String password){
        this.password=password;
    }
    public String getPassword(){
        return this.password;
    }
    public void setImage(byte[] image){
        this.image=image;
    }
    public byte[] getImage(){
        return this.image;
    }
    public boolean isAdmin() {
        return isAdmin;
    }
    public void setIsAdmin(boolean isAdmin){
        this.isAdmin = isAdmin;
    }
}
