package app.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import lombok.Builder;

@Builder
@Entity
@Table(name = "guests")
public class Guest {
	
	@Id
	@Column(name = "id")
	private long id;
	
	@Column(name = "party_id")
	private String partyId;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "rank")
	private int rank;
	
	@Column(name = "role")
	private String role;
	
	@OneToMany(mappedBy="guestId")
	private List<Qube> qubes;
	
	public long getId() {
		return id;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public String getPartyId() {
		return partyId;
	}
	
	public void setPartyId(String partyId) {
		this.partyId = partyId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public int getRank() {
		return rank;
	}
	
	public void setRank(int rank) {
		this.rank = rank;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	public List<Qube> getQubes() {
		return qubes;
	}
	public void setQubes(List<Qube> qubes) {
		this.qubes = qubes;
	}
	
	

}
