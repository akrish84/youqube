package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
