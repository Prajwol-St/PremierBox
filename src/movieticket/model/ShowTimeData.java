package movieticket.model;

/** Simple POJO that represents one show-time row. */
public class ShowTimeData {

    private int    show_id;
    private int    movie_id;
    private String show_time;      // HH:mm

    public ShowTimeData(int movie_id, String show_time) {
        this.movie_id  = movie_id;
        this.show_time = show_time;
    }
    public ShowTimeData(int show_id,int movie_id,String show_time){
        this(movie_id, show_time);
        this.show_id = show_id;
    }

    public int    getShow_id()   { return show_id; }
    public int    getMovie_id()  { return movie_id; }
    public String getShow_time() { return show_time; }

    public void setShow_time(String t){ this.show_time = t; }
}
