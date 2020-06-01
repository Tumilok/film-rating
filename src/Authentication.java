import org.hibernate.*;
import org.hibernate.query.Query;
import org.hibernate.cfg.Configuration;

import javax.persistence.metamodel.EntityType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Authentication {
    private static final SessionFactory ourSessionFactory;

    static {
        try {
            Configuration configuration = new Configuration();
            configuration.configure();

            ourSessionFactory = configuration.buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static Session getSession() throws HibernateException {
        return ourSessionFactory.openSession();
    }

    private static boolean isUserExists(String email) {
        List<User> users = getSession().createCriteria(User.class).list();
        for (User user: users) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public static User register(String email, String password, String firstName, String lastName) {
        if (isUserExists(email)) {
            return null;
        } else {
            final Session session = getSession();
            User user = new User(email, password, firstName, lastName);
            Transaction transaction = session.beginTransaction();
            session.save(user);
            transaction.commit();
            return user;
        }
    }

    public static User login(String email, String password) {
        List<User> users = getSession().createCriteria(User.class).list();
        for (User user: users) {
            if (user.isThisUser(email, password)) {
                return user;
            }
        }
        return null;
    }

    public static List<Actor> getActors(){
        List<Actor> actors = getSession().createCriteria(Actor.class).list();
        return actors;
    }

    public static List<Movie> getMovies(String input) {
        List<Movie> movies = getSession().createCriteria(Movie.class).list();
        List<Movie> listToReturn = new ArrayList<>();
        for (Movie movie: movies) {
            if (movie.isPartOfTitle(input)) {
                listToReturn.add(movie);
            }
        }
        return listToReturn;
    }

    public static void addRating(int movieID, int userId, double rate) {
        final Session session = getSession();
        Movie movie = session.get(Movie.class, movieID);
        User user = session.get(User.class, userId);

        Transaction transaction = session.beginTransaction();

        List<Rating> oldRatings = getSession().createCriteria(Rating.class).list();
        for (Rating oldRating: oldRatings) {
            if (oldRating.getPerson().equals(user) && oldRating.getMovie().equals(movie)) {
                oldRating.setRating(rate);
                oldRating.setDate_rated(new Date().toString());
                session.update(oldRating);
                transaction.commit();
                return;
            }
        }
        Rating newRating = new Rating(movie, user, rate, new Date().toString());
        session.save(newRating);
        transaction.commit();
    }

    public static double getMovieRating(int movieId) {
        List<Rating> ratings = getSession().createCriteria(Rating.class).list();
        if (ratings.size() == 0) {
            return 0.0;
        }
        double ratingsSum = 0.0;
        for (Rating rating: ratings) {
            if (rating.getMovie().getMovieID() == movieId) {
                ratingsSum += rating.getRating();
            }
        }
        return ratingsSum / ratings.size();
    }

    public static void addDirector(Director director){
        final Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.save(director);
        transaction.commit();
    }

    public static void addActor(Actor actor){
        final Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.save(actor);
        transaction.commit();
    }

    public static void addMovie(String title, String description, String yearOfRelease, List<Actor> actors, List<Director> directors) {
        final Session session = getSession();
        List<Movie> movies = getSession().createCriteria(Movie.class).list();
        for (Movie movie: movies) {
            if (movie.getTitle().equals(title)) {
                System.out.println("Taki film uż istnieje w bazie");
                return;
            }
        }
        Transaction transaction = session.beginTransaction();
        Movie movie = new Movie(title, description, yearOfRelease);
        for(Actor actor : actors){
            movie.addActor(actor);
        }
        for(Director director : directors){
            movie.addDirector(director);
        }
        session.save(new Movie(title, description, yearOfRelease));
        transaction.commit();
    }

    public static void main(final String[] args) throws Exception {
        final Session session = getSession();
        try {

            //Actor actor = new Actor("Bill", "Murray");
            //Category category = new Category("Drama");
            //Director director = new Director("Sofia", "Coppola");
            //Movie movie = new Movie("Taxi","About cool taxi", "1997");
            //movie.addActor(actor);
            //movie.addCategory(category);
            //User user = new User("example@somemail.com", "12345678", "Bred", "Lonch");
            //Rating rating = new Rating(movie, user, 9, new Date().toString());
            //Transaction transaction = session.beginTransaction();
            //session.save(actor);
            //session.save(category);
            //session.save(director);
            //session.save(movie);
            //session.save(user);
            //session.save(rating);
            //transaction.commit();

            register("example@cloud.com", "dghmfgm", "Mike", "Simons");
            login("example@somemail.com", "12345678");
            List<Movie> movies = getMovies("taxi");
            System.out.println("------------------------------------------");
            for (Movie movie: movies) {
                System.out.println(movie.getTitle());
            }
            System.out.println("------------------------------------------");


            System.out.println("querying all the managed entities...");
            final Metamodel metamodel = session.getSessionFactory().getMetamodel();
            for (EntityType<?> entityType : metamodel.getEntities()) {
                final String entityName = entityType.getName();
                final Query query = session.createQuery("from " + entityName);
                System.out.println("executing: " + query.getQueryString());
                for (Object o : query.list()) {
                    System.out.println("  " + o);
                }
            }
        } finally {
            session.close();
        }
    }

    public static List<Director> getDirectors() {
        List<Director> directors = getSession().createCriteria(Director.class).list();
        return directors;
    }
}