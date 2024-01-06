import java.util.List;

public class MessageData {
    char phase;
    List<Character> messwerte;
    public MessageData(char phase, List<Character> messwerten) {
        this.messwerte = messwerten;
        this.phase = phase;
    }
}
