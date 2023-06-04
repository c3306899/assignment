package uni.castleone.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import uni.castleone.dto.QueueMessage;

import java.util.concurrent.ThreadLocalRandom;

import static uni.castleone.constant.ApplicationConstant.TOPIC_NAME;

@Configuration
@EnableScheduling
public class KafkaScheduledProducer {

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Scheduled(fixedDelay = 1000 * 10)
    public void scheduleFixedDelayTask() {
        Integer message = ThreadLocalRandom.current().nextInt(0, 10000);
        QueueMessage queueMessage = new QueueMessage(message);
        kafkaTemplate.send(TOPIC_NAME, queueMessage);
        System.out.println(queueMessage.getQuestion());
    }
}
