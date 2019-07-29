package com.br.rmboni.sqsQueueProducer.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MessageService {
	@Autowired
	private SQSService sqsService;
	
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
			for (int i=0; amount <= i; i++) {
		
				logger.info("Enviando mensagem");
			}
			
			return true;
		} catch(Exception e) {
			logger.error("Erro ao enviar msg", e);
		}
		
		return false;
	}
}
