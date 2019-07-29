package com.br.rmboni.sqsQueueProducer.resource;

import com.br.rmboni.sqsQueueProducer.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;

@RestController
@RequestMapping("/queue")
public class QueueResource {
	@Autowired
	private MessageService messageService;
	
	@PostMapping("/create")
	public ResponseEntity createQueue() {
		if (messageService.createQueue()) {
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@DeleteMapping("/remove")
	public ResponseEntity deleteQueue() {
		if (messageService.removeQueue()) {
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
	
	@PostMapping("/send")
	public ResponseEntity sendMessages(@NotNull @RequestBody final int amount) {
		if (messageService.send(amount)) {
			return ResponseEntity.ok().build();
		}
		
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
	}
}
