package com.br.rmboni.sqsQueueProducer.domain;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Data
@RequiredArgsConstructor
public class SQSMessage {
	private final String message;
	private final LocalDateTime timestamp;
	private final int counter;
}
