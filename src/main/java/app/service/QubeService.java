package app.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import app.exceptions.ObjectNotFoundException;
import app.model.Guest;
import app.model.Party;
import app.model.Qube;
import app.repository.QubeRepository;

@Service
public class QubeService {
	
	@Autowired
	private QubeRepository qubeRepository;
	
	@Autowired 
	private PartyService partyService;
	
	public Qube addQube(String partyId, Qube qube) throws ObjectNotFoundException {
	
		Party party = partyService.getParty(partyId);
		Guest guest = null;
		int qubeMaxRank = 0;
		
		for(Guest partyGuest : party.getGuests()) {
			if(qube.getGuestId() == partyGuest.getId()) {
				guest = partyGuest;
			}
		}
		
		if(guest == null) {
			throw new ObjectNotFoundException("Guest with guestID: " + qube.getGuestId() + " Not Found.");
		}
		
		if(guest.getQubes() != null ) {
			for(Qube guestQube : guest.getQubes()) {
				qubeMaxRank = Math.max(qubeMaxRank,  guestQube.getRank());
			}
		}
		
		qube.setRank(qubeMaxRank+1);
		
		return qubeRepository.save(qube);
	}
	
	public Qube getQube(long qubeId) throws ObjectNotFoundException {
		Optional<Qube> qube = qubeRepository.findById(qubeId);
		if(qube.isEmpty()) {
			throw new ObjectNotFoundException("Qube with id: " + qubeId + " not found.");
		}
		return qube.get();
	}
	
	@Transactional
	public void updateQube(Qube qube, String partyId) throws ObjectNotFoundException {
		
		Party party = partyService.getParty(partyId);
		
		Qube existingQube = this.getQube(qube.getId());
		
		List<Qube> updateQubes = new ArrayList<>();
		
		int rankDifference = 0;

		Guest guest = getGuest(party.getGuests(), qube.getGuestId());

		if(guest == null) {
			throw new ObjectNotFoundException("Guest ["+ qube.getGuestId() + "] Not Found");
		}
		
		if(guest.getQubes() == null || guest.getQubes().isEmpty()) {
			throw new ObjectNotFoundException("Guest ["+ qube.getGuestId() + "] has no Qubes");
		}
		
		int lowerbound = qube.getRank();
		int upperbound = qube.getRank();
		
		if(qube.getRank() > existingQube.getRank()) {
			lowerbound = existingQube.getRank();
			rankDifference = -1;
		} else if(qube.getRank() < existingQube.getRank()) {
			upperbound = existingQube.getRank();
			rankDifference = 1;
		}

		if(lowerbound != upperbound) {
			for(Qube guestQube : guest.getQubes()) {
				if(guestQube.getId() != qube.getId() && guestQube.getRank() >= lowerbound && guestQube.getRank() <= upperbound) {
					guestQube.setRank(guestQube.getRank() + rankDifference);
					updateQubes.add(guestQube);
				}
			}
		}
		
		updateQubes.add(qube);
		
		for(Qube updateQube : updateQubes) {
			qubeRepository.save(updateQube);
		}
	}
	
	private Guest getGuest(List<Guest> guests, long guestId) {
		if(guests == null ) {
			return null;
		}
		for(Guest guest : guests) {
			if(guest.getId() == guestId) {
				return guest;
			}
		}
		return null;
	}
	
	public void deleteQube(long qubeId) throws ObjectNotFoundException {
		try {
			qubeRepository.deleteById(qubeId);
		} catch(EmptyResultDataAccessException ae) {
			throw new ObjectNotFoundException(ae.getMessage());
		}
	}

}
