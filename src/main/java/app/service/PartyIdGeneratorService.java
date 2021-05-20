package app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import app.partyIdGenerator.PartyIdGenerator;
import app.repository.PartyIdSequenceRepository;


@Service
public class PartyIdGeneratorService {
	
	@Autowired
	private PartyIdSequenceRepository partyIdSequenceRepository;
	
	
	public String generatePartyId() {
		
		PartyIdGenerator partyIdGenerator = PartyIdGenerator.getInstance(partyIdSequenceRepository);
		return partyIdGenerator.getPartyId();
		
		
	}

}
