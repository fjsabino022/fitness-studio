package com.fsabino.fitness.controller;

import java.util.List;

import javax.inject.Inject;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fsabino.fitness.dto.ClientSubscriptionCodeDTO;
import com.fsabino.fitness.dto.ClientSubscriptionDTO;
import com.fsabino.fitness.exception.ClientSubscriptionNotFoundException;
import com.fsabino.fitness.exception.IlegalProductCodeException;
import com.fsabino.fitness.exception.IlegalSubscriptionCodeException;
import com.fsabino.fitness.exception.SubscriptionNotFoundException;
import com.fsabino.fitness.request.AddClientSubscriptionRequest;
import com.fsabino.fitness.request.UpdateStatusClientSubscriptionRequest;
import com.fsabino.fitness.service.subscription.ClientSubscriptionService;

@RestController
@RequestMapping(value = "/subscription/v1")
public class ClientSubscriptionController {

	private final ClientSubscriptionService clientSubscriptionService;
	
	@Inject
	public ClientSubscriptionController(ClientSubscriptionService clientSubscriptionService) {
		this.clientSubscriptionService = clientSubscriptionService;
	}

	@PostMapping
	public ResponseEntity<ClientSubscriptionCodeDTO> addClientSubscription(
			@RequestBody AddClientSubscriptionRequest addClientSubscriptionRequest) {
		try {
			ClientSubscriptionCodeDTO clientSubscriptionCodeDTO = 
					clientSubscriptionService.addClientSubscription(addClientSubscriptionRequest);
			return ResponseEntity.ok(clientSubscriptionCodeDTO);
		} catch (IlegalProductCodeException | IlegalSubscriptionCodeException e) {
			ClientSubscriptionCodeDTO clientSubscriptionCodeDTO = ClientSubscriptionCodeDTO.builder()
					.message(e.getMessage())
					.buildFailedResponse();
			return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
					.body(clientSubscriptionCodeDTO);
		} catch (SubscriptionNotFoundException e) {
			ClientSubscriptionCodeDTO clientSubscriptionCodeDTO = ClientSubscriptionCodeDTO.builder()
					.message(e.getMessage())
					.buildFailedResponse();
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
					.body(clientSubscriptionCodeDTO);
		}
	}
	
	@GetMapping
	public ResponseEntity<List<ClientSubscriptionDTO>> getActiveClientSubscriptions() {
		return ResponseEntity.ok(clientSubscriptionService.getActiveClientSubscriptions());
	}
	
	@PutMapping("/{clientSubscriptionCode}")
	public ResponseEntity<ClientSubscriptionCodeDTO> updateStatusClientSubscription(
				@PathVariable("clientSubscriptionCode") String clientSubscriptionCode, 
				@RequestBody UpdateStatusClientSubscriptionRequest updateStatusClientSubscriptionRequest)  {
		try {
			return ResponseEntity.ok(clientSubscriptionService.updateStatusClientSubscription(clientSubscriptionCode, 
					updateStatusClientSubscriptionRequest));
		} catch (ClientSubscriptionNotFoundException e) {
			ClientSubscriptionCodeDTO clientSubscriptionCodeDTO = ClientSubscriptionCodeDTO.builder()
					.message(e.getMessage())
					.buildFailedResponse();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(clientSubscriptionCodeDTO);
		}
	}
	
	@DeleteMapping("/{clientSubscriptionCode}")
	public ResponseEntity<ClientSubscriptionCodeDTO> cancelClientSubscription(
				@PathVariable("clientSubscriptionCode") String clientSubscriptionCode)  {
		try {
			return ResponseEntity.ok(clientSubscriptionService.cancelClientSubscription(clientSubscriptionCode));
		} catch (ClientSubscriptionNotFoundException e) {
			ClientSubscriptionCodeDTO clientSubscriptionCodeDTO = ClientSubscriptionCodeDTO.builder()
					.message(e.getMessage())
					.buildFailedResponse();
			return ResponseEntity.status(HttpStatus.NOT_FOUND)
					.body(clientSubscriptionCodeDTO);
		}
	}
}
