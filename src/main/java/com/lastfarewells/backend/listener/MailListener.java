package com.lastfarewells.backend.listener;


import org.springframework.stereotype.Component;

import io.awspring.cloud.sqs.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MailListener {

	@SqsListener("${sqs.queueName}")
	public void receiveMessage(String message) {
		log.info("Received message: " + message);
	}

}
