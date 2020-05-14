import org.hibernate.*;
import org.hibernate.query.Query;
import org.hibernate.cfg.Configuration;

import javax.persistence.metamodel.EntityType;

import java.util.Date;
import java.util.Map;

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

    public static void main(final String[] args) throws Exception {
        final Session session = getSession();
        try {
            Actor actor = new Actor("Bill", "Murray");
            Category category = new Category("Drama");
            Director director = new Director("Sofia", "Coppola");
            Movie movie = new Movie("Lost in Translation","Movie about loneliness in a big city", "2003");
            movie.addActor(actor);
            movie.addCategory(category);
            User user = new User("sswrk", "Szymon", "Swierk", "example@example.pl");
            Rating rating = new Rating(movie, user, 9, new Date().toString());
            Transaction transaction = session.beginTransaction();
            session.save(actor);
            session.save(category);
            session.save(director);
            session.save(movie);
            session.save(user);
            session.save(rating);
            transaction.commit();

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