import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="ACTORS")
public class Actor {
	private Integer actorID;
	private String firstname;
	private String lastname;
	
	public Actor(String firstname, String lastname) {
		this.firstname=firstname;
		this.lastname=lastname;
	}
	
	@Id
    @Column(name = "ACTORID")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
	public Integer getActorID() {
		return this.actorID;
	}
	
	public void setActorID(Integer actorID) { this.actorID = actorID; }
	
	public String getFirstname() { return firstname; } 
	
	public void setFirstname(String firstname) { this.firstname=firstname; } 
	
	public String getLastname() { return lastname; } 
	
	public void setLastname(String lastname) { this.lastname=lastname; } 
}
