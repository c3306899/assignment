package uni.castleone.consumer;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import uni.castleone.constant.ApplicationConstant;
import uni.castleone.dto.PrimesTrackingMessage;
import uni.castleone.dto.QueueMessage;

import java.util.ArrayList;
import java.util.List;

@Component
public class KafkaConsumer {

	@KafkaListener(groupId = ApplicationConstant.GROUP_ID_JSON, topics = ApplicationConstant.TOPIC_NAME, containerFactory = ApplicationConstant.KAFKA_LISTENER_CONTAINER_FACTORY)
	public void receivedMessage(QueueMessage message) {
		writeMessage(message);
	}

	private void writeMessage(QueueMessage queueMessage) {
		long startTime = System.currentTimeMillis();
		List<Integer> list = new ArrayList<>();
		for (int num = 2; num <= queueMessage.getQuestion(); num++) {
			if (isPrime(num)) {
				System.out.print(num  + " ");
				list.add(num);
			}
		}
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.print("    Running time:: " + totalTime);
		System.out.println();
		storeToDashboardService(new PrimesTrackingMessage(list, totalTime));
	}

	private static boolean isPrime(int num) {
		for (int i = 2; i <= Math.sqrt(num); i++) {
			if (num % i == 0) {
				return false;
			}
		}
		return true;
	}

	private void storeToDashboardService(PrimesTrackingMessage primesTrackingMessage) {
		String url = "http://localhost:8081/save";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<PrimesTrackingMessage> entity = new HttpEntity<>(primesTrackingMessage, headers);
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<String> responseEntity = restTemplate.postForEntity(url, entity, String.class);
	}
}
