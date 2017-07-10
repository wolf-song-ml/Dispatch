package com.ttd.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

/**
 * 消息消费者
 * 
 * @author wolf
 */
@Component
public class MsgConsumer {
	private static final Logger logger = LoggerFactory.getLogger(MsgConsumer.class);
	
	@KafkaListener(topics = { "order-notice-topic" })
	public void processMessage(String content) {

		logger.info(">>>recieve msg:"+content);
		
	}

}
