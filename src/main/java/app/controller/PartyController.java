package app.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.exceptions.ObjectNotFoundException;
import app.log.LogMessageBuilder;
import app.model.Party;
import app.model.requestresponse.CreatePartyRequest;
import app.model.requestresponse.CreatePartyResponse;
import app.model.requestresponse.GetPartyResponse;
import app.service.PartyService;

import static app.controller.ActionsConstants.*;


@RestController
public class PartyController {
	
	@Autowired
	private PartyService partyService;
	
	private static final String BASE_URI_PATH = "/parties";
	
	
	@PostMapping(BASE_URI_PATH)
	public @ResponseBody ResponseEntity<CreatePartyResponse> createParty(@RequestBody CreatePartyRequest createPartyRequest){
		
		String errorMessage;
		HttpStatus httpStatus;
		Exception exception = null;
		LogMessageBuilder logMessageBuilder = new LogMessageBuilder(CREATE_PARTY);
		logMessageBuilder.buildRequestStartMessage();
		try {
			Validator.emptyValueCheck(createPartyRequest.getName(), "Party Name");
			
			Party party = partyService.createParty(createPartyRequest.getName());
			
			String successResponseMessage = logMessageBuilder.buildSuccessMessage(party + " Created.");
			
			System.out.println(successResponseMessage);
			
			
			CreatePartyResponse response = new CreatePartyResponse(successResponseMessage);
			response.setId(party.getId());
			response.setName(party.getName());
			
			return new ResponseEntity<CreatePartyResponse>(response, HttpStatus.OK);
		
		} catch(IllegalArgumentException ie) {

			errorMessage = logMessageBuilder.buildExceptionMessage(ie.getMessage());
			exception = ie;
			httpStatus =  HttpStatus.BAD_REQUEST;
			
		} catch (Exception e) {

			errorMessage = logMessageBuilder.buildExceptionMessage("Internal Server Error.");
			exception = e;
			httpStatus =  HttpStatus.INTERNAL_SERVER_ERROR;
			
		}
		
		System.out.println(errorMessage);
		exception.printStackTrace();
		
		CreatePartyResponse response = new CreatePartyResponse(errorMessage);
		
		return new ResponseEntity<CreatePartyResponse>(response, httpStatus);
	}
	
	@GetMapping(BASE_URI_PATH + "/{partyId}")
	public @ResponseBody ResponseEntity<GetPartyResponse> getParty(@PathVariable("partyId") String partyId){
		
		String errorMessage;
		HttpStatus httpStatus;
		Exception exception = null;
		LogMessageBuilder logMessageBuilder = new LogMessageBuilder(GET_PARTY);
		logMessageBuilder.buildRequestStartMessage();
		
		try {
			Validator.emptyValueCheck(partyId, "Party Id");
			
			Party party = partyService.getParty(partyId);
			
			String successResponseMessage = logMessageBuilder.buildSuccessMessage("Found party");
			
			System.out.println(successResponseMessage);
			
			
			GetPartyResponse response = new GetPartyResponse(successResponseMessage);
			
			response.setId(party.getId());
			response.setName(party.getName());
			response.setGuests(party.getGuests());
			response.setCreatedAt(party.getCreatedAt());
			
			return new ResponseEntity<GetPartyResponse>(response, HttpStatus.OK);
		
		} catch(ObjectNotFoundException on) {
			
			errorMessage = logMessageBuilder.buildExceptionMessage(on.getMessage());
			exception = on;
			httpStatus =  HttpStatus.NOT_FOUND;
			
		} catch(IllegalArgumentException ie) {
			
			errorMessage = logMessageBuilder.buildExceptionMessage(ie.getMessage());
			exception = ie;
			httpStatus =  HttpStatus.BAD_REQUEST;
			
		} catch (Exception e) {

			errorMessage = logMessageBuilder.buildExceptionMessage("Internal Server Error.");
			exception = e;
			httpStatus =  HttpStatus.INTERNAL_SERVER_ERROR;
			
		}
		
		System.out.println(errorMessage);
		exception.printStackTrace();
		
		GetPartyResponse response = new GetPartyResponse(errorMessage);
		
		return new ResponseEntity<GetPartyResponse>(response, httpStatus);
		
	}


}
