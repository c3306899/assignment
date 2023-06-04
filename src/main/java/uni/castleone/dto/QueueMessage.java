package uni.castleone.dto;

public class QueueMessage {
	private Integer question;

	public QueueMessage() {
	}

	public QueueMessage(Integer question) {
		this.question = question;
	}

	public Integer getQuestion() {
		return question;
	}

	public void setQuestion(Integer question) {
		this.question = question;
	}
}
