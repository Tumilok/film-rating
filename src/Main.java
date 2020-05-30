import org.hibernate.*;
import org.hibernate.query.Query;
import org.hibernate.cfg.Configuration;

import javax.persistence.metamodel.EntityType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Main {
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

    private static User register(String email, String password, String firstName, String lastName) {
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

    private static User login(String email, String password) {
        List<User> users = getSession().createCriteria(User.class).list();
        for (User user: users) {
            if (user.isThisUser(email, password)) {
                return user;
            }
        }
        return null;
    }

    private static List<Movie> getMovies(String input) {
        List<Movie> movies = getSession().createCriteria(Movie.class).list();
        List<Movie> listToReturn = new ArrayList<>();
        for (Movie movie: movies) {
            if (movie.isPartOfTitle(input)) {
                listToReturn.add(movie);
            }
        }
        return listToReturn;
    }

    private static void rateMovie(int movieID, int userId, double rate) {
        final Session session = getSession();
        Movie movie = session.get(Movie.class, movieID);
        User user = session.get(User.class, userId);
        Rating rating = new Rating(movie, user, rate, new Date().toString());
        Transaction transaction = session.beginTransaction();
        session.save(rating);
        transaction.commit();
    }

    private static double getMovieRating(int movieId) {
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
}