package com.br.rmboni.sqsQueueProducer.domain;

import lombok.Data;

import java.util.List;

@Data
public class SendMessageResponse {
	private final List<SentMessage> sentMessages;
}
