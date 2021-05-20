package app.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "party_id_sequence")
public class PartyIdSequence {
	
	@Override
	public String toString() {
		return "PartyIdSequence [id=" + id + ", lastUsedNumber=" + lastUsedNumber + ", cacheCount=" + cacheCount
				+ ", partyIdLength=" + partyIdLength + ", maxPartyNumber=" + maxPartyNumber + "]";
	}

	@Id
	@Column(name = "id")
	private int id;
	
	@Column(name = "last_used_number")
	private int lastUsedNumber;
	
	@Column(name = "cache_count")
	private int cacheCount;
	
	@Column(name = "party_id_length")
	private int partyIdLength;
	
	@Column(name = "max_party_number")
	private int maxPartyNumber;
	
	public int getLastUsedNumber() {
		return lastUsedNumber;
	}

	public void setLastUsedNumber(int lastUsedNumber) {
		this.lastUsedNumber = lastUsedNumber;
	}

	public int getCacheCount() {
		return cacheCount;
	}

	public void setCacheCount(int cacheCount) {
		this.cacheCount = cacheCount;
	}

	public int getPartyIdLength() {
		return partyIdLength;
	}

	public void setPartyIdLength(int partyIdLength) {
		this.partyIdLength = partyIdLength;
	}

	public int getMaxPartyNumber() {
		return maxPartyNumber;
	}

	public void setMaxPartyNumber(int maxPartyNumber) {
		this.maxPartyNumber = maxPartyNumber;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
