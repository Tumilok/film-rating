import org.hibernate.*;
import org.hibernate.query.Query;
import org.hibernate.cfg.Configuration;

import javax.persistence.metamodel.EntityType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Authentication {
    private static final SessionFactory ourSessionFactory;
    private static User user;

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

    public static void setUser(User user) {
        Authentication.user = user;
    }

    public static User getUser() {
        return user;
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

    public static boolean register(String email, String password, String firstName, String lastName) {
        if (isUserExists(email)) return false;
        final Session session = getSession();
        User user = new User(email, password, firstName, lastName);
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        Authentication.user = user;
        return true;
    }

    public static boolean login(String email, String password) {
        List<User> users = getSession().createCriteria(User.class).list();
        for (User user: users) {
            if (user.isThisUser(email, password)) {
                Authentication.user = user;
                return true;
            }
        }
        return false;
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

    public static boolean addRating(int movieID, double rate) {
        if (rate < 0 || rate > 10) return false;
        final Session session = getSession();
        Movie movie = session.get(Movie.class, movieID);

        Transaction transaction = session.beginTransaction();

        List<Rating> oldRatings = getSession().createCriteria(Rating.class).list();
        for (Rating oldRating: oldRatings) {
            if (oldRating.getPerson().equals(user) && oldRating.getMovie().equals(movie)) {
                oldRating.setRating(rate);
                oldRating.setDate_rated(new Date().toString());
                session.update(oldRating);
                transaction.commit();
                return true;
            }
        }
        Rating newRating = new Rating(movie, user, rate, new Date().toString());
        session.save(newRating);
        transaction.commit();
        return true;
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

    public static void addMovie(String title, String description, String yearOfRelease) {
        final Session session = getSession();
        List<Movie> movies = getSession().createCriteria(Movie.class).list();
        for (Movie movie: movies) {
            if (movie.getTitle().equals(title)) {
                System.out.println("Taki film uż istnieje w bazie");
                return;
            }
        }
        Transaction transaction = session.beginTransaction();
        session.save(new Movie(title, description, yearOfRelease));
        transaction.commit();
    }

    public static void main(final String[] args) throws Exception {
        final Session session = getSession();
        try {
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