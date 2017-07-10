package com.ttd.config;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaOperations;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.ProducerListener;
import org.springframework.stereotype.Component;

import com.ttd.interceptor.GlobalInterceptor;

/**
 * 消息生产者
 * 
 * @author wolf
 */
@Component
public class MsgProducer {

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	private static final Logger logger = LoggerFactory.getLogger(MsgProducer.class);

	public void send(String msg) {
		kafkaTemplate.send("order-notice-topic", msg);

		kafkaTemplate.metrics();
		kafkaTemplate.execute(new KafkaOperations.ProducerCallback<String, String, Object>() {
			@Override
			public Object doInKafka(Producer<String, String> producer) { 
				logger.info(">>>>这里可以执行kafka api原生操作");
				return null; 
			} 
		});
		 

		// 消息发送的监听器，用于回调返回信息
		kafkaTemplate.setProducerListener(new ProducerListener<String, String>() {
			@Override
			public void onSuccess(String topic, Integer partition, String key, String value, RecordMetadata recordMetadata) {
				logger.info("kafka producer:topic-" + topic	+ ",partition-" + partition + ",key-" + key
						+ ",value-" + value + ",meta-" + recordMetadata.toString());
			}

			@Override
			public void onError(String topic, Integer partition, String key, String value, Exception exception) {
				logger.info("kafka producer:topic-" + topic	+ ",partition-" + partition + ",key-" + key
						+ ",value-" + value + ",exception-"	+ exception.getMessage());
				exception.printStackTrace();
			}

			@Override
			public boolean isInterestedInSuccess() {
				return false;
			}
			
		});
		
	}

}
