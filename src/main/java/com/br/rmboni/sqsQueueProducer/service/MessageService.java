package com.br.rmboni.sqsQueueProducer.service;

import com.br.rmboni.sqsQueueProducer.domain.SQSMessage;
import com.br.rmboni.sqsQueueProducer.persistence.model.SentMessage;
import com.br.rmboni.sqsQueueProducer.repository.SentMessagesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class MessageService {
	@Autowired
	private SQSService<SQSMessage> sqsService;
	
	@Autowired
	private SentMessagesRepository sentMessagesRepository;
	
	private String queueURL;
	
	private final Logger logger = LoggerFactory.getLogger(MessageService.class);
	
	public boolean createQueue() {
		try {
			queueURL = sqsService.createQueue();
			logger.info("Fila criada {}", queueURL);
			return true;
		} catch (Exception e) {
			logger.error("Erro ao criar fila", e);
		}
		
		return false;
	}
	
	public boolean removeQueue() {
		try {
			sqsService.removeQueue(queueURL);
			logger.info("Fila excluida {}", queueURL);
			return true;
		} catch (Exception e) {
			logger.error("Erro ao excluir fila", e);
		}
		
		return false;
	}
	
	public boolean send(int amount) {
		try {
			for (int i=0; i < amount; i++) {
				SQSMessage messageBean = new SQSMessage(LocalDateTime.now().toString(), LocalDateTime.now(), i);
				final LocalDateTime start = LocalDateTime.now();
				sqsService.sendMessage(messageBean, queueURL);
				final LocalDateTime finish = LocalDateTime.now();
				
				SentMessage sentMessage = new SentMessage();
				sentMessage.setMessage(sqsService.getString(messageBean));
				sentMessage.setTimestamp(start);
				sentMessage.setTimeToSend(Double.valueOf(Duration.between(start, finish).toMillis()));
				sentMessagesRepository.save(sentMessage);
				
				logger.info("Mensagem enviada {}, start {}, finish {}, duration {}", messageBean, start, finish, Duration.between(start, finish).toMillis());
			}
			
			return true;
		} catch(Exception e) {
			logger.error("Erro ao enviar msg", e);
		}
		
		return false;
	}
}
