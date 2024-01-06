public class TransitionMatrix {
    String name;
    float ungunstigProbability;
    float gunstigProbability;

    TransitionMatrix(String name, float u, float g){
        this.name = name;
        this.ungunstigProbability = u;
        this.gunstigProbability = g;
    }
}
