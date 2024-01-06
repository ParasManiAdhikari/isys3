import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String trainingdata = "src/train.txt";
        String evaldata = "src/eval.txt";
        float gewinn = classifier(trainingdata, evaldata);
        System.out.println("GEWINN : " + gewinn);
    }

    private static float classifier(String trainingdataFile, String evaldataFile) throws IOException {
        List<MessageData> traindata = readFromFile(trainingdataFile);
        List<MessageData> evaldata = readFromFile(evaldataFile);
        //System.out.println(traindata);
        MarkovClassifier classifier = new MarkovClassifier();
        List<TransitionMatrix> transitionMatrix = classifier.train(traindata);
        float gewinn = classifier.evaluate(transitionMatrix, evaldata);
        //System.out.println(gewinn);
        return  gewinn;
    }


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