import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
public class Category{
    @Id
    private String name;

    public Category(String name){
        this.name = name;
    }

    public Category() {}

    public String toString() { return name; }

}
