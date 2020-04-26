import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="ACTORS")
public class Actor {
	private Integer actorID;
	private String firstname;
	private String lastname;
	
	@Id
	@Column(name="ACTORID")
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Integer getId() {
		return actorID;
	}
	
	public Actor(String firstname, String lastname) {
		this.actorID=getId();
		this.firstname=firstname;
		this.lastname=lastname;
	}
	
	public String getFirstName() { return firstname; } 
}
