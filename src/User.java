import javax.persistence.*;

@Entity
@Table(name="USERS")
public class User {
    @Id
    @GeneratedValue
    private int userID;
    private String username;
    private String firstname;
    private String lastname;
    private String email;

    public User(String username, String firstname, String lastname, String email){
        this.username = username;
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
    }

    public User(String username, String email){
        this.username = username;
        this.email = email;
    }

    protected User() {}

}
