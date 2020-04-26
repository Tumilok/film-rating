
import org.hibernate.*;
 import org.hibernate.boot.*;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
  
 public class Main {
  
    public static void main(String[] args) {
    	SessionFactory factory = new Configuration()
    	        .configure() // configures settings from hibernate.cfg.xml
    	        .buildSessionFactory();
        
        Session session = factory.openSession();
        session.beginTransaction();
 
        Actor actor = (Actor) session.load(Actor.class, new Integer(1));
        
        if(actor != null)
            System.out.println(actor.getFirstName());
 
        session.close();
        factory.close();
    }
  
 }
