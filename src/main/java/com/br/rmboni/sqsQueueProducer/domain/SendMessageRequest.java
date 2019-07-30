package com.br.rmboni.sqsQueueProducer.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

@Data
public class SendMessageRequest {
	@NotNull
	private Integer amount;
}
