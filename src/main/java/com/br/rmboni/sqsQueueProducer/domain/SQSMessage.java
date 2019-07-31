package com.br.rmboni.sqsQueueProducer.domain;

import lombok.Data;

@Data
public class SQSMessage {
	private final String message;
	private final String timestamp;
	private final int counter;
}
