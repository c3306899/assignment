package uni.castleone.dto;

import java.util.List;


public class PrimesTrackingMessage {

    private List<Integer> answer;

    private Long timeTaken;

    public PrimesTrackingMessage(List<Integer> answer, Long timeTaken) {
        this.answer = answer;
        this.timeTaken = timeTaken;
    }

    public List<Integer> getAnswer() {
        return answer;
    }

    public void setAnswer(List<Integer> answer) {
        this.answer = answer;
    }

    public Long getTimeTaken() {
        return timeTaken;
    }

    public void setTimeTaken(Long timeTaken) {
        this.timeTaken = timeTaken;
    }
}
