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
    private int rating;
    private String date_rated;

    public Rating(Movie movie, User user, int rating, String date){
        this.movie = movie;
        this.person = user;
        this.rating = rating;
        this.date_rated = date;
    }

    protected Rating(){}

}