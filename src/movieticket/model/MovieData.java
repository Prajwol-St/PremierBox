package movieticket.model;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Hp
 */
public class MovieData {
    private int movie_id;
    private String title;
    private String genre;
    private String duration;
    private String publishedDate;
   
    
    public MovieData(String title, String genre, String duration, String publishedDate) {
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.publishedDate = publishedDate;
        
        System.out.println("here2: "+ title + genre + duration + publishedDate);
       
    }
    
      public MovieData(int movie_id, String title, String genre, String duration, String publishedDate) {
        this.movie_id = movie_id;
        this.title = title;
        this.genre = genre;
        this.duration = duration;
        this.publishedDate = publishedDate;
       
    }
    
    
    
    public int getMovie_id() {
        return movie_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getDate() {
        return publishedDate;
    }

    public void setDate(String date) {
        this.publishedDate = date;
    }

//    public byte[] getPosterPath() {
//        return posterPath;
//    }
//
//    public void setPosterPath(byte[] posterPath) {
//        this.posterPath = posterPath;
//    }
}
