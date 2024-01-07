import java.util.ArrayList;
import java.util.List;

/**
 * The Classifier class represents a classifier that uses Markov chains to train using training data and predict whether to sow or not sow based on evaluation data.
 */

public class Classifier {

    // Transition Matrix probabilities of each markov - chains
    public TransitionMatrix hnTM;
    public TransitionMatrix hmTM;
    public TransitionMatrix hhTM;
    public TransitionMatrix nhTM;
    public TransitionMatrix nmTM;
    public TransitionMatrix nnTM;
    public TransitionMatrix mhTM;
    public TransitionMatrix mnTM;
    public TransitionMatrix mmTM;

    // Count of markov - chain occurrence in Ungünstig Phase
    float hnU = 0;
    float hmU = 0;
    float hhU = 0;
    float nhU = 0;
    float nmU = 0;
    float nnU = 0;
    float mhU = 0;
    float mnU = 0;
    float mmU = 0;

    // Count of markov - chain occurrence in Günstig Phase
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
    public int countOfTotal_h_chains_ungunstig;
    public int countOfTotal_m_chains_ungunstig;
    public int countOfTotal_n_chains_ungunstig;
    public int countOfTotal_h_chains_gunstig;
    public int countOfTotal_m_chains_gunstig;
    public int countOfTotal_n_chains_gunstig;

    /**
     * Trains the classifier and calculates transition probabilities for Ungunstig and Gunstig phases.
     * @param traindata List of MessageData for training.
     * @return List of TransitionMatrix representing calculated probabilities.
     */

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
                        countOfTotal_h_chains_ungunstig++;
                    } else if (currentMesswert == 'h' && nextMesswert == 'm') {
                        hmU++;
                        countOfTotal_h_chains_ungunstig++;
                    } else if (currentMesswert == 'h' && nextMesswert == 'h') {
                        hhU++;
                        countOfTotal_h_chains_ungunstig++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'm') {
                        nmU++;
                        countOfTotal_n_chains_ungunstig++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'h') {
                        nhU++;
                        countOfTotal_n_chains_ungunstig++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'n') {
                        nnU++;
                        countOfTotal_n_chains_ungunstig++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'h') {
                        mhU++;
                        countOfTotal_m_chains_ungunstig++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'n') {
                        mnU++;
                        countOfTotal_m_chains_ungunstig++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'm') {
                        mmU++;
                        countOfTotal_m_chains_ungunstig++;
                    }
                }

                if(currentPhase == 'G') {
                    if (currentMesswert == 'h' && nextMesswert == 'n') {
                        hnG++;
                        countOfTotal_h_chains_gunstig++;
                    } else if (currentMesswert == 'h' && nextMesswert == 'm') {
                        hmG++;
                        countOfTotal_h_chains_gunstig++;
                    } else if (currentMesswert == 'h' && nextMesswert == 'h') {
                        hhG++;
                        countOfTotal_h_chains_gunstig++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'm') {
                        nmG++;
                        countOfTotal_n_chains_gunstig++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'h') {
                        nhG++;
                        countOfTotal_n_chains_gunstig++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'n') {
                        nnG++;
                        countOfTotal_n_chains_gunstig++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'h') {
                        mhG++;
                        countOfTotal_m_chains_gunstig++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'n') {
                        mnG++;
                        countOfTotal_m_chains_gunstig++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'm') {
                        mmG++;
                        countOfTotal_m_chains_gunstig++;
                    }
                }
            }
        }

        // Saving TransitionMatrix probabilities
        hnTM = new TransitionMatrix("hn", hnU / countOfTotal_h_chains_ungunstig, hnG / countOfTotal_h_chains_gunstig);
        hmTM = new TransitionMatrix("hm", hmU / countOfTotal_h_chains_ungunstig, hmG / countOfTotal_h_chains_gunstig);
        hhTM = new TransitionMatrix("hh", hhU / countOfTotal_h_chains_ungunstig, hhG / countOfTotal_h_chains_gunstig);
        nhTM = new TransitionMatrix("nh", nhU / countOfTotal_n_chains_ungunstig, nhG / countOfTotal_n_chains_gunstig);
        nmTM = new TransitionMatrix("nm", nmU / countOfTotal_n_chains_ungunstig, nmG / countOfTotal_n_chains_gunstig);
        nnTM = new TransitionMatrix("nn", nnU / countOfTotal_n_chains_ungunstig, nnG / countOfTotal_n_chains_gunstig);
        mhTM = new TransitionMatrix("mh", mhU / countOfTotal_m_chains_ungunstig, mhG / countOfTotal_m_chains_gunstig);
        mnTM = new TransitionMatrix("mn", mnU / countOfTotal_m_chains_ungunstig, mnG / countOfTotal_m_chains_gunstig);
        mmTM = new TransitionMatrix("mm", mmU / countOfTotal_m_chains_ungunstig, mmG / countOfTotal_m_chains_gunstig);

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

    /**
     * Evaluates the classifier on evaluation data and calculates total win/loss based on predicted and actual phases.
     * @param evaldata List of MessageData for evaluation.
     * @return Total win/loss based on the evaluation.
     */

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

                // Calculate probabilities for both phases
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
