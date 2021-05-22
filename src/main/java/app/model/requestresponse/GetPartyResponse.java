package app.model.requestresponse;

import java.util.List;

import app.model.Guest;

public class GetPartyResponse extends MessageResponse {

	public GetPartyResponse(String message) {
		super(message);
	}

	private String id;
	private String name;
	private long createdAt;
	private List<Guest> guests;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public List<Guest> getGuests() {
		return guests;
	}

	public void setGuests(List<Guest> guests) {
		this.guests = guests;
	}

}
