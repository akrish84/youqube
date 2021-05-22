package app.controller;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.exceptions.ObjectExistsException;
import app.exceptions.ObjectNotFoundException;
import app.log.LogMessageBuilder;
import app.model.Guest;
import app.model.Party;
import app.model.Roles;
import app.model.requestresponse.AddGuestRequest;
import app.model.requestresponse.AddGuestResponse;
import app.service.GuestService;
import app.service.PartyService;
import static app.controller.ActionsConstants.*;

@RestController
public class GuestController {
	
	@Autowired
	private GuestService guestService;
	
	@Autowired
	private PartyService partyService;
	
	
	private static final String BASE_URI_PATH = "/guests";
	
	private static Set<String> userRoles = Roles.getAllRolesAsString();
	
	@PostMapping(BASE_URI_PATH)
	public @ResponseBody ResponseEntity<AddGuestResponse> addGuest(@RequestBody AddGuestRequest addGuestRequest){
		
		String errorMessage;
		HttpStatus httpStatus;
		Exception exception = null;
		LogMessageBuilder logMessageBuilder = new LogMessageBuilder(ADD_GUEST);
		logMessageBuilder.buildRequestStartMessage();
		
		try {
			Validator.emptyValueCheck(addGuestRequest.getName(), "Guest Name");
			Validator.emptyValueCheck(addGuestRequest.getRole(), "Guest Role");
			Validator.containsCheck(addGuestRequest.getRole(), userRoles, "Guest Role");
			
			Guest guest = Guest.builder().partyId(addGuestRequest.getPartyId())
					.name(addGuestRequest.getName())
					.role(addGuestRequest.getRole())
					.build();
			
			Party party = partyService.getParty(guest.getPartyId()); 
			
			Guest addedGuest = guestService.addGuestToParty(guest, party);
			
			String successResponseMessage = logMessageBuilder.buildSuccessMessage( addedGuest + " added to " + party);
			
			System.out.println(successResponseMessage);
			
			AddGuestResponse response = new AddGuestResponse(successResponseMessage);
			
			response.setId(addedGuest.getId());
			response.setName(addedGuest.getName());
			response.setRank(addedGuest.getRank());
			response.setRole(addedGuest.getRole());
			response.setPartyId(addedGuest.getPartyId());
			
			
			return new ResponseEntity<AddGuestResponse>(response, HttpStatus.OK);
		
		} catch(ObjectNotFoundException on) {
			
			errorMessage = logMessageBuilder.buildExceptionMessage(on.getMessage());
			exception = on;
			httpStatus =  HttpStatus.NOT_FOUND;
			
		} catch(ObjectExistsException oe) {
			
			errorMessage = logMessageBuilder.buildExceptionMessage(oe.getMessage());
			exception = oe;
			httpStatus =  HttpStatus.CONFLICT;
			
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
		
		AddGuestResponse response = new AddGuestResponse(errorMessage);
		
		return new ResponseEntity<AddGuestResponse>(response, httpStatus);
	}
}
