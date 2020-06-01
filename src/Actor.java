import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Actor {
    @Id
    @GeneratedValue
    private int ActorID;
    private String firstname;
    private String lastname;

    public Actor(String firstname, String lastname){
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Actor() {}

    @Override
    public String toString() {
        return firstname + ' ' + lastname;
    }
}
