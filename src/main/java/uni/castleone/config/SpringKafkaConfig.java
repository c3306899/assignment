package uni.castleone.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import uni.castleone.constant.ApplicationConstant;
import uni.castleone.dto.QueueMessage;

import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableKafka
public class SpringKafkaConfig {

	@Bean
	public ProducerFactory<String, Object> producerFactory() {
		Map<String, Object> configMap = new HashMap<>();
		configMap.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, ApplicationConstant.KAFKA_LOCAL_SERVER_CONFIG);
		configMap.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		configMap.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		configMap.put(JsonDeserializer.TRUSTED_PACKAGES, "uni.castleone.dto.QueueMessage");
		return new DefaultKafkaProducerFactory<String, Object>(configMap);
	}

	@Bean
	public KafkaTemplate<String, Object> kafkaTemplate() {
		return new KafkaTemplate<>(producerFactory());
	}

	@Bean
	public ConsumerFactory<String, QueueMessage> consumerFactory() {
		JsonDeserializer<QueueMessage> deserializer = new JsonDeserializer<>(QueueMessage.class);
		deserializer.addTrustedPackages("*");
		deserializer.setUseTypeMapperForKey(true);

		Map<String, Object> configMap = new HashMap<>();
		configMap.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, ApplicationConstant.KAFKA_LOCAL_SERVER_CONFIG);
		configMap.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		configMap.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
		return new DefaultKafkaConsumerFactory<>(configMap, new StringDeserializer(), deserializer);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, QueueMessage> kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, QueueMessage> factory = new ConcurrentKafkaListenerContainerFactory<String, QueueMessage>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	@Bean
	public NewTopic primeEvents() {
		return new NewTopic("primeEvents", 3, (short)3);
	}
}
