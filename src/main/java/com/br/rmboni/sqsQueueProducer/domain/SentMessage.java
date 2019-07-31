package com.br.rmboni.sqsQueueProducer.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SentMessage {
	private String message;
	private String messageId;
	private LocalDateTime timestamp;
	private Double millesSecondToSend;
}
