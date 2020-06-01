import org.hibernate.annotations.ColumnTransformer;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="USERS")
public class User {
    @Id
    @GeneratedValue
    private int userID;

    private String email;

    //@Column
    //@ColumnTransformer(read = "pgp_sym_decrypt(password, 'mySecretKey')", write = "pgp_sym_encrypt(?, 'mySecretKey')")
    private String password;

    private String firstName;
    private String lastName;

    protected User() {}

    public User(String email, String password, String firstName, String lastName) {
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return userID == user.userID &&
                email.equals(user.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userID, email);
    }

    public int getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public boolean isThisUser(String email, String password) {
        return this.email.equals(email) && this.password.equals(password);
    }
}