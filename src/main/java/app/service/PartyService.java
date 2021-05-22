package app.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.exceptions.ObjectNotFoundException;
import app.model.Party;
import app.repository.PartyRepository;

@Service
public class PartyService {
	
	@Autowired
	PartyIdGeneratorService partyIdGeneratorService;
	
	@Autowired
	PartyRepository partyRepository;
	
	public Party createParty(String partyName) {
		
		String partyId = partyIdGeneratorService.generatePartyId();
		
		Party party = Party.builder()
				.createdAt(System.currentTimeMillis())
				.name(partyName)
				.id(partyId)
				.build();
		
		return partyRepository.save(party);
		
	}
	
	public Party getParty(String partyId) throws ObjectNotFoundException {
		Optional<Party> optionalParty = partyRepository.findById(partyId);
		if(optionalParty.isEmpty()) {
			throw new ObjectNotFoundException("Party with Id: " + partyId + " not found" );
		}
		return optionalParty.get();
	}
}
