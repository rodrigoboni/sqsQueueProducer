package com.br.rmboni.sqsQueueProducer.service;

import com.amazonaws.services.sqs.model.SendMessageResult;
import com.br.rmboni.sqsQueueProducer.domain.SQSMessage;
import com.br.rmboni.sqsQueueProducer.domain.SendMessageResponse;
import com.br.rmboni.sqsQueueProducer.domain.SentMessage;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

@Service
public class MessageService {
	@Autowired
	private SQSService<SQSMessage> sqsService;
	
	private String queueURL;
	
	private final Logger logger = LoggerFactory.getLogger(MessageService.class);
	
	public void createQueue() {
		if (queueURL != null) {
			return;
		}
		
		queueURL = sqsService.createQueue();
		logger.info("Fila criada {}", queueURL);
	}
	
	public void removeQueue() {
		sqsService.removeQueue(queueURL);
		logger.info("Fila excluida {}", queueURL);
	}
	
	public SendMessageResponse send(int amount) {
		ArrayList<SentMessage> sentMessages = new ArrayList<>();
		for (int i = 0; i < amount; i++) {
			SQSMessage messageBean = new SQSMessage(RandomStringUtils.randomAlphanumeric(10), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(LocalDateTime.now()), i);
			final LocalDateTime start = LocalDateTime.now();
			SendMessageResult sendMessageResult = sqsService.sendMessage(messageBean, queueURL);
			final LocalDateTime finish = LocalDateTime.now();
			
			SentMessage sentMessage = new SentMessage();
			sentMessage.setMessage(sqsService.getString(messageBean).length() > 255 ? sqsService.getString(messageBean).substring(0, 254) : sqsService.getString(messageBean));
			sentMessage.setMessageId(sendMessageResult.getMessageId());
			sentMessage.setTimestamp(start);
			sentMessage.setMillesSecondToSend(Double.valueOf(Duration.between(start, finish).toMillis()));
			
			sentMessages.add(sentMessage);
			logger.info("Mensagem enviada {}, start {}, finish {}, duration {}", messageBean, start, finish, Duration.between(start, finish).toMillis());
		}
		
		return new SendMessageResponse(sentMessages);
	}
}
