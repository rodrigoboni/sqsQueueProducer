package com.br.rmboni.sqsQueueProducer.service;

import com.amazonaws.services.sqs.AmazonSQS;
import com.amazonaws.services.sqs.AmazonSQSClientBuilder;
import com.amazonaws.services.sqs.model.CreateQueueRequest;
import com.amazonaws.services.sqs.model.CreateQueueResult;
import com.amazonaws.services.sqs.model.DeleteQueueResult;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SQSService {
	@Value("${sqs.queue.name}")
	private String sqsQueueName;
	
	private final AmazonSQS sqs = AmazonSQSClientBuilder.defaultClient();
	
	public String createQueue() throws Exception {
		final Map<String, String> queueAttributes = new HashMap<>();
//		queueAttributes.put("teste", "teste");
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
	
	}
}
