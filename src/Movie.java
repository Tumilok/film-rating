import javax.persistence.*;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
public class Movie {
    @Id
    @GeneratedValue
    private int movieID;
    private String title;
    private String description;
    private String yearOfRelease;
    @OneToMany
    @JoinColumn(name = "MovieActorsFK")
    private Set<Actor> actors = new LinkedHashSet<>();
    @OneToMany
    @JoinColumn(name = "MovieDirectorsFK")
    private Set<Director> directors = new LinkedHashSet<>();
    @OneToMany
    @JoinColumn(name = "MovieCategoriesFK")
    private Set<Category> categories = new LinkedHashSet<>();

    public Movie(String title, String description, String yearOfRelease){
        this.title = title;
        this.description = description;
        this.yearOfRelease = yearOfRelease;
    }

    public Movie() {}

    public int getMovieID() {
        return movieID;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getYearOfRelease() {
        return yearOfRelease;
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public Set<Director> getDirectors() {
        return directors;
    }

    public Set<Category> getCategories() {
        return categories;
    }

    public void addActor(Actor actor){
        actors.add(actor);
    }

    public void addDirector(Director director){
        directors.add(director);
    }

    public void addCategory(Category category){
        categories.add(category);
    }

    public boolean isPartOfTitle(String input) {
        return title.toLowerCase().contains(input.toLowerCase());
    }

}
