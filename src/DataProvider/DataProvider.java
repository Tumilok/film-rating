package DataProvider;

import Container.*;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.security.AlgorithmParameters;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.List;

public class DataProvider {
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

    public static User getUser() {
        return user;
    }

    public static boolean login(String email, String password) throws GeneralSecurityException {
        byte[] salt = "12345678".getBytes();
        int iterationCount = 40000;
        int keyLength = 128;
        SecretKeySpec key = createSecretKey(password.toCharArray(),
                salt, iterationCount, keyLength);
        List<User> users = getUsers();
        for (User user: users) {
            String user_password = decrypt(user.getPassword(), key);
            if (password.equals(user_password) && user.getEmail().equals(email)) {
                DataProvider.user = user;
                return true;
            }
        }
        return false;
    }

    public static boolean addUser(User user) {
        if (user == null || isUserExist(user)) return false;
        final Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.save(user);
        transaction.commit();
        DataProvider.user = user;
        return true;
    }

    public static boolean addDirector(Director director) {
        if (director == null || isDirectorExist(director)) return false;
        final Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.save(director);
        transaction.commit();
        return true;
    }

    public static boolean addActor(Actor actor) {
        if (actor == null || isActorExist(actor)) return false;
        final Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.save(actor);
        transaction.commit();
        return true;
    }

    public static boolean addMovie(Movie movie) {
        if (movie == null || isMovieExist(movie)) return false;
        final Session session = getSession();
        Transaction transaction = session.beginTransaction();
        session.save(movie);
        transaction.commit();
        return true;
    }

    public static boolean addRating(Rating rating) {
        if (rating == null || rating.getRating() < 0 || rating.getRating() > 10) return false;
        final Session session = getSession();
        Transaction transaction = session.beginTransaction();
        List<Rating> oldRatings = getRatings();
        for (Rating oldRating: oldRatings) {
            if (oldRating.equals(rating)) {
                oldRating.setRating(rating.getRating());
                oldRating.setDate_rated(new Date().toString());
                session.update(oldRating);
                transaction.commit();
                return true;
            }
        }
        session.save(rating);
        transaction.commit();
        return true;
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

    public static List<User> getUsers() { return getSession().createCriteria(User.class).list(); }

    public static List<Director> getDirectors() {
        return getSession().createCriteria(Director.class).list();
    }

    public static List<Actor> getActors() {
        return getSession().createCriteria(Actor.class).list();
    }

    public static List<Rating> getRatings() {
        return getSession().createCriteria(Rating.class).list();
    }

    public static double getMovieRating(Movie movie) {
        List<Rating> ratings = getRatings();
        if (movie == null || ratings.size() == 0) return 0.0;
        double ratingsSum = 0.0;
        int ratingsNum = 0;
        for (Rating rating: ratings) {
            if (rating.getMovie().equals(movie)) {
                ratingsSum += rating.getRating();
                ratingsNum++;
            }
        }
        return ratingsSum / ratingsNum;
    }

    private static boolean isUserExist(User user) {
        for (User oneUser: getUsers())
            if (oneUser.equals(user))
                return true;
        return false;
    }

    private static boolean isDirectorExist(Director director) {
        for (Director oneDirector: getDirectors())
            if (oneDirector.equals(director))
                return true;
        return false;
    }

    private static boolean isActorExist(Actor actor) {
        for (Actor oneActor: getActors())
            if (oneActor.equals(actor))
                return true;
        return false;
    }

    private static boolean isMovieExist(Movie movie) {
        for (Movie oneMovie: getMovies(""))
            if (oneMovie.equals(movie))
                return true;
        return false;
    }

    public static SecretKeySpec createSecretKey(char[] password, byte[] salt, int iterationCount, int keyLength) throws NoSuchAlgorithmException, InvalidKeySpecException {
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA512");
        PBEKeySpec keySpec = new PBEKeySpec(password, salt, iterationCount, keyLength);
        SecretKey keyTmp = keyFactory.generateSecret(keySpec);
        return new SecretKeySpec(keyTmp.getEncoded(), "AES");
    }

    public static String encrypt(String property, SecretKeySpec key) throws GeneralSecurityException {
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.ENCRYPT_MODE, key);
        AlgorithmParameters parameters = pbeCipher.getParameters();
        IvParameterSpec ivParameterSpec = parameters.getParameterSpec(IvParameterSpec.class);
        byte[] cryptoText = pbeCipher.doFinal(property.getBytes(StandardCharsets.UTF_8));
        byte[] iv = ivParameterSpec.getIV();
        return base64Encode(iv) + ":" + base64Encode(cryptoText);
    }

    private static String base64Encode(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    private static String decrypt(String string, SecretKeySpec key) throws ArrayIndexOutOfBoundsException, GeneralSecurityException {
        String iv = string.split(":")[0];
        String property = string.split(":")[1];
        Cipher pbeCipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        pbeCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(base64Decode(iv)));
        return new String(pbeCipher.doFinal(base64Decode(property)), StandardCharsets.UTF_8);
    }

    private static byte[] base64Decode(String property) {
        return Base64.getDecoder().decode(property);
    }
}