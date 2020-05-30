import javax.persistence.*;

@Entity
public class Rating {
    @Id
    @GeneratedValue
    private int ratingID;
    @OneToOne
    @JoinColumn(name = "RatingMovieFK")
    private Movie movie;
    @OneToOne
    @JoinColumn(name = "RatingUserFK")
    private User person;
    private double rating;
    private String date_rated;

    public Rating(Movie movie, User user, double rating, String date){
        this.movie = movie;
        this.person = user;
        this.rating = rating;
        this.date_rated = date;
    }

    protected Rating(){}

    public int getRatingID() {
        return ratingID;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public User getPerson() {
        return person;
    }

    public void setPerson(User person) {
        this.person = person;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getDate_rated() {
        return date_rated;
    }

    public void setDate_rated(String date_rated) {
        this.date_rated = date_rated;
    }
}