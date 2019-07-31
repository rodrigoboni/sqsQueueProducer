package com.br.rmboni.sqsQueueProducer.resource;

import com.br.rmboni.sqsQueueProducer.domain.SendMessageRequest;
import com.br.rmboni.sqsQueueProducer.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/queue")
public class QueueResource {
	@Autowired
	private MessageService messageService;
	
	@DeleteMapping("/remove")
	public ResponseEntity deleteQueue() {
		messageService.removeQueue();
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/send")
	public ResponseEntity sendMessages(@Valid @RequestBody final SendMessageRequest messageRequest) {
		messageService.createQueue();
		return ResponseEntity.ok().body(messageService.send(messageRequest.getAmount()));
	}
}
