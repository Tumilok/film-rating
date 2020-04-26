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
    @GeneratedValue
    @Column(name = "ACTORID", unique = true, nullable = false)
	public Integer getActorID() {
		return this.actorID;
	}
	
	@Transient
	public void setActorID(Integer actorID) { this.actorID = actorID; }
	
	@Transient
	public String getFirstname() { return firstname; } 
	
	@Transient
	public void setFirstname(String firstname) { this.firstname=firstname; } 
	
	@Transient
	public String getLastname() { return lastname; } 
	
	@Transient
	public void setLastname(String lastname) { this.lastname=lastname; } 
}
