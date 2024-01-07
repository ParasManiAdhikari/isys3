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

    // State of transitions
    public int UngunstigStateH;
    public int UngunstigStateM;
    public int UngunstigStateN;
    public int GunstigStateH;
    public int GunstigStateM;
    public int GunstigStateN;

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
                        UngunstigStateH++;
                    } else if (currentMesswert == 'h' && nextMesswert == 'm') {
                        hmU++;
                        UngunstigStateH++;
                    } else if (currentMesswert == 'h' && nextMesswert == 'h') {
                        hhU++;
                        UngunstigStateH++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'm') {
                        nmU++;
                        UngunstigStateN++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'h') {
                        nhU++;
                        UngunstigStateN++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'n') {
                        nnU++;
                        UngunstigStateN++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'h') {
                        mhU++;
                        UngunstigStateM++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'n') {
                        mnU++;
                        UngunstigStateM++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'm') {
                        mmU++;
                        UngunstigStateM++;
                    }
                }

                if(currentPhase == 'G') {
                    if (currentMesswert == 'h' && nextMesswert == 'n') {
                        hnG++;
                        GunstigStateH++;
                    } else if (currentMesswert == 'h' && nextMesswert == 'm') {
                        hmG++;
                        GunstigStateH++;
                    } else if (currentMesswert == 'h' && nextMesswert == 'h') {
                        hhG++;
                        GunstigStateH++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'm') {
                        nmG++;
                        GunstigStateN++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'h') {
                        nhG++;
                        GunstigStateN++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'n') {
                        nnG++;
                        GunstigStateN++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'h') {
                        mhG++;
                        GunstigStateM++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'n') {
                        mnG++;
                        GunstigStateM++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'm') {
                        mmG++;
                        GunstigStateM++;
                    }
                }
            }
        }

        hnTM = new TransitionMatrix("hn", hnU / UngunstigStateH, hnG / GunstigStateH);
        hmTM = new TransitionMatrix("hm", hmU / UngunstigStateH, hmG / GunstigStateH);
        hhTM = new TransitionMatrix("hh", hhU / UngunstigStateH, hhG / GunstigStateH);
        nhTM = new TransitionMatrix("nh", nhU / UngunstigStateN, nhG / GunstigStateN);
        nmTM = new TransitionMatrix("nm", nmU / UngunstigStateN, nmG / GunstigStateN);
        nnTM = new TransitionMatrix("nn", nnU / UngunstigStateN, nnG / GunstigStateN);
        mhTM = new TransitionMatrix("mh", mhU / UngunstigStateM, mhG / GunstigStateM);
        mnTM = new TransitionMatrix("mn", mnU / UngunstigStateM, mnG / GunstigStateM);
        mmTM = new TransitionMatrix("mm", mmU / UngunstigStateM, mmG / GunstigStateM);

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

    public float evaluate(List<MessageData> evaldata) {
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
                System.out.println("Calculated:\tAussaat \t\t| Actualphase ist günstig:\t\t+20");
            } else if (calculatedPhase == 'G' && actualPhase == 'U') {
                gewinn -= 12;
                System.out.println("Calculated:\tAussaat \t\t| Actualphase ist ungünstig:\t-12");
            } else if (calculatedPhase == 'U' && actualPhase == 'G') {
                gewinn -= 2;
                System.out.println("Calculated:\tKein Aussaat \t| Actualphase ist günstig:\t\t-2");
            } else if (calculatedPhase == 'U' && actualPhase == 'U') {
                gewinn -= 1;
                System.out.println("Calculated:\tKein Aussaat \t| Actualphase ist ungünstig:\t-1 ");
            }
        }
        return gewinn;
    }
}
