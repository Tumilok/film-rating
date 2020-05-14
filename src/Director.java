import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Director {
    @Id
    @GeneratedValue
    private int directorID;
    private String firstname;
    private String lastname;

    public Director(String firstname, String lastname){
        this.firstname = firstname;
        this.lastname = lastname;
    }

    public Director() {}

}
