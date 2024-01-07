import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String trainingDataPath = "src/train.txt";
        List<MessageData> traindata = readFromFile(trainingDataPath);
        String evalDataPath = "src/eval.txt";
        List<MessageData> evaldata = readFromFile(evalDataPath);
        Classifier classifier = new Classifier();
        classifier.train(traindata);
        float gewinn = classifier.evaluate(evaldata);
        System.out.println("TOTAL GEWINN: " + gewinn);
    }
    /**
     * Reads MessageData from a file and returns a list of MessageData.
     * @return List of MessageData read from the file.
     */
    public static List<MessageData> readFromFile(String fileName) throws IOException {
        List<MessageData> messageList = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            while ((line = br.readLine()) != null) {
                char phase = line.charAt(0);
                List<Character> messwerten = new ArrayList<>();
                for (int i = 2; i < line.length(); i += 2) {
                    messwerten.add(line.charAt(i));
                }
                MessageData messageData = new MessageData(phase, messwerten);
                messageList.add(messageData);
            }
        }

        return messageList;
    }
}