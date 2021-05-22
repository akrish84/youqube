package app.controller;

import static app.controller.ActionsConstants.ADD_QUBE;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import app.exceptions.ObjectNotFoundException;
import app.log.LogMessageBuilder;
import app.model.Qube;
import app.model.requestresponse.AddQubeRequest;
import app.model.requestresponse.AddQubeResponse;
import app.model.requestresponse.DeleteQubeResponse;
import app.model.requestresponse.UpdateQubeRequest;
import app.model.requestresponse.UpdateQubeResponse;
import app.service.QubeService;

@RestController
public class QubeController {
	
	@Autowired
	private QubeService qubeService;
	
	
	private static final String BASE_URI_PATH = "/qubes";
	
	@PostMapping(BASE_URI_PATH)
	public @ResponseBody ResponseEntity<AddQubeResponse> addQube(@RequestBody AddQubeRequest addQubeRequest){
		
		String errorMessage;
		HttpStatus httpStatus;
		Exception exception = null;
		LogMessageBuilder logMessageBuilder = new LogMessageBuilder(ADD_QUBE);
		logMessageBuilder.buildRequestStartMessage();
		
		try {

			Validator.emptyValueCheck(addQubeRequest.getLink(), "Qube Link");
			
			Qube qube = Qube.builder()
					.guestId(addQubeRequest.getGuestId())
					.link(addQubeRequest.getLink())
					.build();
			
			
			Qube addedQube = qubeService.addQube(addQubeRequest.getPartyId(), qube);
			
			String successResponseMessage = logMessageBuilder.buildSuccessMessage( addedQube + " Added.");
			
			System.out.println(successResponseMessage);
			
			AddQubeResponse response = new AddQubeResponse(successResponseMessage);
			
			response.setId(addedQube.getId());
			response.setGuestId(addedQube.getGuestId());
			response.setLink(addedQube.getLink());
			response.setRank(addedQube.getRank());
			
			return new ResponseEntity<AddQubeResponse>(response, HttpStatus.OK);
		
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
		
		AddQubeResponse response = new AddQubeResponse(errorMessage);
		
		return new ResponseEntity<AddQubeResponse>(response, httpStatus);
	}
	
	@PutMapping(BASE_URI_PATH)
	public @ResponseBody ResponseEntity<UpdateQubeResponse> updateQube(@RequestBody UpdateQubeRequest updateQubeRequest){
		
		String errorMessage;
		HttpStatus httpStatus;
		Exception exception = null;
		LogMessageBuilder logMessageBuilder = new LogMessageBuilder(ADD_QUBE);
		logMessageBuilder.buildRequestStartMessage();
		
		try {

			Validator.emptyValueCheck(updateQubeRequest.getLink(), "Qube Link");
			Validator.emptyValueCheck(updateQubeRequest.getPartyId(), "Party Link");
			
			Qube qube = Qube.builder()
					.id(updateQubeRequest.getId())
					.link(updateQubeRequest.getLink())
					.rank(updateQubeRequest.getRank())
					.guestId(updateQubeRequest.getGuestId())
					.build();
			
			qubeService.updateQube(qube, updateQubeRequest.getPartyId());
					
			String successResponseMessage = logMessageBuilder.buildSuccessMessage( qube + " Updated.");
			
			System.out.println(successResponseMessage);
			
			UpdateQubeResponse response = new UpdateQubeResponse(successResponseMessage);
			
			response.setId(qube.getId());
			response.setGuestId(qube.getGuestId());
			response.setLink(qube.getLink());
			response.setRank(qube.getRank());
			
			return new ResponseEntity<UpdateQubeResponse>(response, HttpStatus.OK);
		
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
		
		UpdateQubeResponse response = new UpdateQubeResponse(errorMessage);
		
		return new ResponseEntity<UpdateQubeResponse>(response, httpStatus);
	}
	
	@DeleteMapping(BASE_URI_PATH + "/{qubeId}")
	public @ResponseBody ResponseEntity<DeleteQubeResponse> deleteQube(@PathVariable("qubeId") long qubeId){
		String errorMessage;
		HttpStatus httpStatus;
		Exception exception = null;
		LogMessageBuilder logMessageBuilder = new LogMessageBuilder(ADD_QUBE);
		logMessageBuilder.buildRequestStartMessage();
		
		try {

			Validator.defaultValueCheck(qubeId, "Qube Id");
			
			qubeService.deleteQube(qubeId);
			
			String successResponseMessage = logMessageBuilder.buildSuccessMessage( "Qube with id " + qubeId + " deleted.");
			
			System.out.println(successResponseMessage);
			
			return new ResponseEntity<>(HttpStatus.OK);
		
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
		
		DeleteQubeResponse response = new DeleteQubeResponse(errorMessage);
		
		return new ResponseEntity<DeleteQubeResponse>(response, httpStatus);
	}

}
