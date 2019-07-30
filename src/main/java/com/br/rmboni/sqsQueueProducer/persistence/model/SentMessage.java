package com.br.rmboni.sqsQueueProducer.persistence.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Entity
@Data
public class SentMessage {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@NotNull
	private String message;
	
	@NotNull
	private LocalDateTime timestamp;
	
	@NotNull
	private Double timeToSend;
}
