package com.br.rmboni.sqsQueueProducer.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.*;
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
	
	public String createQueue() {
		final Map<String, String> queueAttributes = new HashMap<>();
		queueAttributes.put("ReceiveMessageWaitTimeSeconds", "10");
		
		CreateQueueResult queue = sqs.createQueue(new CreateQueueRequest()
				.withQueueName(sqsQueueName)
				.withAttributes(queueAttributes));
		
		return queue.getQueueUrl();
	}
	
	public void removeQueue(final String queueURL) {
		DeleteQueueResult deleteQueueResult = sqs.deleteQueue(queueURL);
	}
	
	public SendMessageResult sendMessage(final String message, final String queueURL) {
		return sqs.sendMessage(new SendMessageRequest(queueURL, message));
	}
	
	public SendMessageResult sendMessage(final T messageBean, final String queueURL) {
		String serializedMessage = getString(messageBean);
		return sendMessage(serializedMessage, queueURL);
	}
	
	public String getString(T messageBean) {
		try {
			return objectMapper.writeValueAsString(messageBean);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		return "";
	}
}
