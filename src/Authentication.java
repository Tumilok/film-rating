import org.hibernate.*;
import org.hibernate.query.Query;
import org.hibernate.cfg.Configuration;

import javax.persistence.metamodel.EntityType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;

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

    public static User register(String email, String password, String firstName, String lastName) throws GeneralSecurityException, UnsupportedEncodingException {
        byte[] salt = new String("12345678").getBytes();
        int iterationCount = 40000;
        int keyLength = 128;
        SecretKeySpec key = createSecretKey(password.toCharArray(),
                salt, iterationCount, keyLength);
        password = encrypt(password, key);
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

    public static User login(String email, String password) throws GeneralSecurityException, IOException {
        byte[] salt = new String("12345678").getBytes();
        int iterationCount = 40000;
        int keyLength = 128;
        SecretKeySpec key = createSecretKey(password.toCharArray(),
                salt, iterationCount, keyLength);
        List<User> users = getSession().createCriteria(User.class).list();
        for (User user: users) {
            System.out.println(user.getPassword());
            String user_password = decrypt(user.getPassword(), key);
            System.out.println(password);
            if (password.equals(user_password) && user.getEmail().equals(email)) {
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

    public static List<Director> getDirectors() {
        List<Director> directors = getSession().createCriteria(Director.class).list();
        return directors;
    }

    private static SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(keyTmp.getEncoded(), "AES");
    }

    private static String encrypt(String property, SecretKeySpec key) throws GeneralSecurityException, UnsupportedEncodingException {
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters parameters = pbeCipher.getParameters();
        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
        byte[] cryptoText = pbeCipher.doFinal(property.getBytes("UTF-8"));
        byte[] iv = ivParameterSpec.getIV();
        return base64Encode(iv) + ":" + base64Encode(cryptoText);
    }

    private static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static String decrypt(String string, SecretKeySpec key) throws ArrayIndexOutOfBoundsException, GeneralSecurityException, IOException {
        String iv = string.split(":")[0];
        String property = string.split(":")[1];
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
        return new String(pbeCipher.doFinal(base64Decode(property)), "UTF-8");
    }

    private static byte[] base64Decode(String property) throws IOException {
        return Base64.getDecoder().decode(property);
    }
}