import java.util.ArrayList;
import java.util.List;

public class Classifier {

    // Transition Matrix probabilities for markov - chains
    public TransitionMatrix hnTM;
    public TransitionMatrix hmTM;
    public TransitionMatrix hhTM;
    public TransitionMatrix nhTM;
    public TransitionMatrix nmTM;
    public TransitionMatrix nnTM;
    public TransitionMatrix mhTM;
    public TransitionMatrix mnTM;
    public TransitionMatrix mmTM;

    // Count of markov - chain occurrence in Ungünstig Data
    float hnU = 0;
    float hmU = 0;
    float hhU = 0;
    float nhU = 0;
    float nmU = 0;
    float nnU = 0;
    float mhU = 0;
    float mnU = 0;
    float mmU = 0;

    // Count of markov - chain occurrence in Günstig Data
    float hnG = 0;
    float hmG = 0;
    float hhG = 0;
    float nhG = 0;
    float nmG = 0;
    float nnG = 0;
    float mhG = 0;
    float mnG = 0;
    float mmG = 0;

    public List<TransitionMatrix> train(List<MessageData> traindata) {
        List<TransitionMatrix> result = new ArrayList<>();
        for(MessageData  messagedata : traindata){
            char currentPhase = messagedata.phase;
            List<Character> messwerten = messagedata.messwerte;

            for (int i = 0; i < messwerten.size() - 1; i++) {
                char currentMesswert = messwerten.get(i);
                char nextMesswert = messwerten.get(i + 1);
                if(currentPhase == 'U') {
                    if (currentMesswert == 'h' && nextMesswert == 'n') {
                        hnU++;
                    } else if (currentMesswert == 'h' && nextMesswert == 'm') {
                        hmU++;
                    } else if (currentMesswert == 'h' && nextMesswert == 'h') {
                        hhU++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'm') {
                        nmU++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'h') {
                        nhU++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'n') {
                        nnU++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'h') {
                        mhU++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'n') {
                        mnU++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'm') {
                        mmU++;
                    }
                }

                if(currentPhase == 'G') {
                    if (currentMesswert == 'h' && nextMesswert == 'n') {
                        hnG++;
                    } else if (currentMesswert == 'h' && nextMesswert == 'm') {
                        hmG++;
                    } else if (currentMesswert == 'h' && nextMesswert == 'h') {
                        hhG++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'm') {
                        nmG++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'h') {
                        nhG++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'n') {
                        nnG++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'h') {
                        mhG++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'n') {
                        mnG++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'm') {
                        mmG++;
                    }
                }
            }
        }

        hnTM = new TransitionMatrix("hn", calculateUnGunstigProbability(hnU, hnG), calculateGunstigProbability(hnU, hnG));
        hmTM = new TransitionMatrix("hm", calculateUnGunstigProbability(hmU, hmG), calculateGunstigProbability(hmU, hmG));
        hhTM = new TransitionMatrix("hh", calculateUnGunstigProbability(hhU, hhG), calculateGunstigProbability(hhU, hhG));
        nhTM = new TransitionMatrix("nh", calculateUnGunstigProbability(nhU, nhG), calculateGunstigProbability(nhU, nhG));
        nmTM = new TransitionMatrix("nm", calculateUnGunstigProbability(nmU, nmG), calculateGunstigProbability(nmU, nmG));
        nnTM = new TransitionMatrix("nn", calculateUnGunstigProbability(nnU, nnG), calculateGunstigProbability(nnU, nnG));
        mhTM = new TransitionMatrix("mh", calculateUnGunstigProbability(mhU, mhG), calculateGunstigProbability(mhU, mhG));
        mnTM = new TransitionMatrix("mn", calculateUnGunstigProbability(mnU, mnG), calculateGunstigProbability(mnU, mnG));
        mmTM = new TransitionMatrix("mm", calculateUnGunstigProbability(mmU, mmG), calculateGunstigProbability(mmU, mmG));

        result.add(hnTM);
        result.add(hmTM);
        result.add(hhTM);
        result.add(nhTM);
        result.add(nmTM);
        result.add(nnTM);
        result.add(mhTM);
        result.add(mnTM);
        result.add(mmTM);

        return result;
    }

    private float calculateGunstigProbability(float ungunstigCount, float gunstigCount) {
        return gunstigCount / (ungunstigCount + gunstigCount);
    }

    private float calculateUnGunstigProbability(float ungunstigCount, float gunstigCount) {
        return ungunstigCount / (ungunstigCount + gunstigCount);
    }

    public float evaluate(List<TransitionMatrix> transitionMatrix, List<MessageData> evaldata) {
        int gewinn = 0;
        for(MessageData  messagedata : evaldata){
            char actualPhase = messagedata.phase;
            char calculatedPhase = '\0';

            float totalungunstigPrb = 0;
            float totalgunstigPrb = 0;
            List<Character> messwerten = messagedata.messwerte;
            for (int i = 0; i < messwerten.size() - 1; i++) {
                char currentMesswert = messwerten.get(i);
                char nextMesswert = messwerten.get(i + 1);
                if(currentMesswert == 'h' && nextMesswert == 'm'){
                    totalungunstigPrb += hmTM.ungunstigProbability;
                    totalgunstigPrb += hmTM.gunstigProbability;
                }else if (currentMesswert == 'h' && nextMesswert == 'h') {
                    totalungunstigPrb += hhTM.ungunstigProbability;
                    totalgunstigPrb += hhTM.gunstigProbability;
                }else if (currentMesswert == 'h' && nextMesswert == 'n') {
                    totalungunstigPrb += hnTM.ungunstigProbability;
                    totalgunstigPrb += hnTM.gunstigProbability;
                }else if (currentMesswert == 'n' && nextMesswert == 'n') {
                    totalungunstigPrb += nnTM.ungunstigProbability;
                    totalgunstigPrb += nnTM.gunstigProbability;
                }else if (currentMesswert == 'n' && nextMesswert == 'h') {
                    totalungunstigPrb += nhTM.ungunstigProbability;
                    totalgunstigPrb += nhTM.gunstigProbability;
                }else if (currentMesswert == 'n' && nextMesswert == 'm') {
                    totalungunstigPrb += nmTM.ungunstigProbability;
                    totalgunstigPrb += nmTM.gunstigProbability;
                }else if (currentMesswert == 'm' && nextMesswert == 'n') {
                    totalungunstigPrb += mnTM.ungunstigProbability;
                    totalgunstigPrb += mnTM.gunstigProbability;
                }else if (currentMesswert == 'm' && nextMesswert == 'h') {
                    totalungunstigPrb += mhTM.ungunstigProbability;
                    totalgunstigPrb += mhTM.gunstigProbability;
                }else if (currentMesswert == 'm' && nextMesswert == 'm') {
                    totalungunstigPrb += mmTM.ungunstigProbability;
                    totalgunstigPrb += mmTM.gunstigProbability;
                }
            }
            if(totalgunstigPrb > totalungunstigPrb){
                calculatedPhase = 'G';
            }else if(totalgunstigPrb < totalungunstigPrb) {
                calculatedPhase = 'U';
            }

            if (calculatedPhase == 'G' && actualPhase == 'G') {
                gewinn += 20;
                System.out.println("calculatedPhase ist günstig und actualphase ist günstig: +20");
            } else if (calculatedPhase == 'U' && actualPhase == 'G') {
                gewinn -= 2;
                System.out.println("calculatedPhase ist ungünstig und actualphase ist günstig: -2");
            } else if (calculatedPhase == 'G' && actualPhase == 'U') {
                gewinn -= 12;
                System.out.println("calculatedPhase ist günstig und actualphase ist ungünstig: -12");
            } else if (calculatedPhase == 'U' && actualPhase == 'U') {
                gewinn -= 1;
                System.out.println("calculatedPhase ist ungünstig und actualphase ist ungünstig: -1 ");
            }

        }
        return gewinn;
    }
}
