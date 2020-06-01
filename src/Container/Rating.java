package Container;

import javax.persistence.*;
import java.util.Objects;

@Entity
public class Rating {
    @Id
    @GeneratedValue
    private int ratingID;
    @OneToOne
    @JoinColumn(name = "RatingMovie_FK")
    private Movie movie;
    @OneToOne
    @JoinColumn(name = "RatingUser_FK")
    private User person;
    private double rating;
    private String date_rated;

    protected Rating() {}

    public Rating(Movie movie, User user, double rating, String date) {
        this.movie = movie;
        this.person = user;
        this.rating = rating;
        this.date_rated = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Rating)) return false;
        Rating rating = (Rating) o;
        return movie.equals(rating.movie) &&
                person.equals(rating.person);
    }

    @Override
    public int hashCode() {
        return Objects.hash(movie, person);
    }

    public int getRatingID() {
        return ratingID;
    }

    public Movie getMovie() { return movie; }

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