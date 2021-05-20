package app.partyIdGenerator;

import app.model.PartyIdSequence;
import app.repository.PartyIdSequenceRepository;

public class PartyIdGenerator {

//	private static final int DEFAULT_PARTY_ID_LENGTH = 4;
//	private static final int DEFAULT_MAX_PARTY_NUMBER = 14776335;
	
	private static final String VALID_CHARACTERS = "1234567890abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";
	
	
	private PartyIdSequenceRepository partyIdSequenceRepository;

	private int lastUsedNumber;
	private int cacheCount;
	private int numbersUsed;
	private int partyIdLength;
	private int maxPartyNumber;

	private static PartyIdGenerator instance;
	
	
	private PartyIdGenerator(PartyIdSequenceRepository partyIdSequenceRepository) {

		if (partyIdSequenceRepository == null) {
			throw new IllegalArgumentException("Party Id Sequence Repository is null");
		}

		this.partyIdSequenceRepository = partyIdSequenceRepository;
		
		refreshPartyIdSequence();

	}
	
	public static PartyIdGenerator getInstance(PartyIdSequenceRepository partyIdSequenceRepository) {
		if(instance == null) {
			instance = new PartyIdGenerator(partyIdSequenceRepository);
		}
		return instance;
	}
	
	public String getPartyId() {
		if(numbersUsed == cacheCount) {
			refreshPartyIdSequence();
		}
		int nextNumber = lastUsedNumber + 1;
		String partyId = convertNumberToAlphanumericId(nextNumber);
		lastUsedNumber++;
		numbersUsed++;
		
		if(nextNumber == maxPartyNumber) {
			resetPartyIdSequenceNumber();
		}
		
		return partyId;
	}

	private void refreshPartyIdSequence() {
		PartyIdSequence partyIdSequence = partyIdSequenceRepository.findAll().iterator().next();
		
		if (partyIdSequence == null) {
			throw new IllegalStateException("No party sequence information found");
		}
		
		this.lastUsedNumber = partyIdSequence.getLastUsedNumber();
		this.cacheCount = partyIdSequence.getCacheCount();
		this.numbersUsed = 0;
		this.partyIdLength = partyIdSequence.getPartyIdLength();
		this.maxPartyNumber = partyIdSequence.getMaxPartyNumber();
		
		
		partyIdSequence.setLastUsedNumber(lastUsedNumber + cacheCount);
		
		partyIdSequenceRepository.save(partyIdSequence);
		
	}
	
	private void resetPartyIdSequenceNumber() {
		PartyIdSequence partyIdSequence = partyIdSequenceRepository.findAll().iterator().next();
		
		if (partyIdSequence == null) {
			throw new IllegalStateException("No party sequence information found");
		}
		
		partyIdSequence.setLastUsedNumber(0);
		
		partyIdSequenceRepository.save(partyIdSequence);
	}

	private String convertNumberToAlphanumericId(int number) {

		long magicNumber = number;
		long wholeNum = 0;
		int remainder = 0;

		int modVal = VALID_CHARACTERS.length();

		StringBuilder id = new StringBuilder();

		if (number == 0) {
			id.append(VALID_CHARACTERS.charAt(0));
		}

		while (magicNumber != 0) {

			wholeNum = (long) Math.floor(magicNumber / modVal);
			remainder = (int) magicNumber % modVal;
			id.append(VALID_CHARACTERS.charAt(remainder));
			magicNumber = wholeNum;

		}

		while (id.length() < partyIdLength) {
			id.append(0);
		}

		return id.reverse().toString();
	}

	private Integer convertAlphanumericIdToNumber(String id) {

		Integer base10Number = 0;

		for (int i = 0; i < id.length(); i++) {

			char c = id.charAt(i);

			int index = VALID_CHARACTERS.indexOf(c);

			if (index == -1) {
				throw new IllegalArgumentException("Character '" + c
						+ "' is not a valid character. The only valid characters are: " + VALID_CHARACTERS);
			}
			base10Number += (int) Math.pow(VALID_CHARACTERS.length(), id.length() - i - 1) * index;
		}
		return base10Number;

	}

}
