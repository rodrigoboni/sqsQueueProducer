package com.br.rmboni.sqsQueueProducer.repository;

import com.br.rmboni.sqsQueueProducer.persistence.model.SentMessage;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SentMessagesRepository extends JpaRepository<SentMessage, Long> {
}
