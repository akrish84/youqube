package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.exceptions.ObjectExistsException;
import app.model.Guest;
import app.model.Party;
import app.model.Roles;
import app.repository.GuestRepository;

@Service
public class GuestService {
	
	@Autowired
	private GuestRepository guestRepository;
	
	public Guest addGuestToParty(Guest guest, Party party) throws ObjectExistsException {
		
		int maxRank = 0;
		String admin = Roles.ADMIN.getRole();
		boolean isAdmin = guest.getRole().equals(admin);
		if(party.getGuests()!= null) {
			for(Guest partyGuest : party.getGuests()) {
				
				if(partyGuest.getName().equals(guest.getName())) {
					throw new ObjectExistsException("Guest Name already exists");
				}
				if(isAdmin && partyGuest.getRole().equals(admin)) {
					throw new IllegalArgumentException("Party already has an admin: " + partyGuest.getName());
				}
				maxRank = Math.max(maxRank, partyGuest.getRank());
			}
		}
		
		guest.setRank(maxRank + 1);
	
		return guestRepository.save(guest);
	}

}
