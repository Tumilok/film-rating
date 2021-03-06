package Container;

import javax.persistence.*;
import java.util.*;

@Entity
public class Movie {
    @Id
    @GeneratedValue
    private int movieID;
    private String title;
    private String description;
    private String yearOfRelease;
    @OneToMany
    @JoinColumn(name = "MovieActors_FK")
    private Set<Actor> actors = new LinkedHashSet<>();
    @OneToMany
    @JoinColumn(name = "MovieDirectors_FK")
    private Set<Director> directors = new LinkedHashSet<>();

    public Movie() {}

    public Movie(String title, String description, String yearOfRelease){
        this.title = title;
        this.description = description;
        this.yearOfRelease = yearOfRelease;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Movie)) return false;
        Movie movie = (Movie) o;
        return title.equals(movie.title) &&
                yearOfRelease.equals(movie.yearOfRelease);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, yearOfRelease);
    }

    public int getMovieID() {
        return movieID;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setYearOfRelease(String yearOfRelease) {
        this.yearOfRelease = yearOfRelease;
    }

    public String getYearOfRelease() {
        return yearOfRelease;
    }

    public void addActor(Actor actor){
        actors.add(actor);
    }

    public void addDirector(Director director){
        directors.add(director);
    }

    public Set<Actor> getActors() {
        return actors;
    }

    public Set<Director> getDirectors() {
        return directors;
    }

    public boolean isPartOfTitle(String input) {
        return title.toLowerCase().contains(input.toLowerCase());
    }
}