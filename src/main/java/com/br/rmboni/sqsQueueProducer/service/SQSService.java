package com.br.rmboni.sqsQueueProducer.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.DeleteQueueResult;
import com.amazonaws.services.sqs.model.SendMessageRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SQSService<T> {
	@Value("${sqs.queue.name}")
	private String sqsQueueName;
	
	private final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
	
	private final ObjectMapper objectMapper = new ObjectMapper();
	
	public String createQueue() throws Exception {
		final Map<String, String> queueAttributes = new HashMap<>();
		// TODO DEFINIR PARAMETROS DA FILA
		
		CreateQueueResult queue = sqs.createQueue(new CreateQueueRequest()
				.withQueueName(sqsQueueName)
				.withAttributes(queueAttributes));
		
		return queue.getQueueUrl();
	}
	
	public void removeQueue(final String queueURL) throws Exception {
		DeleteQueueResult deleteQueueResult = sqs.deleteQueue(queueURL);
	}
	
	public void sendMessage(final String message, final String queueURL) throws Exception {
		sqs.sendMessage(new SendMessageRequest(queueURL, message));
	}
	
	public void sendMessage(final T messageBean, final String queueURL) throws Exception {
		String serializedMessage = getString(messageBean);
		sendMessage(serializedMessage, queueURL);
	}
	
	public String getString(T messageBean) throws JsonProcessingException {
		return objectMapper.writeValueAsString(messageBean);
	}
}
