import java.util.ArrayList;
import java.util.List;

public class MarkovClassifier {
    public TransitionMatrix hnTM;
    public TransitionMatrix hmTM;
    public TransitionMatrix hhTM;
    public TransitionMatrix nhTM;
    public TransitionMatrix nmTM;
    public TransitionMatrix nnTM;
    public TransitionMatrix mhTM;
    public TransitionMatrix mnTM;
    public TransitionMatrix mmTM;

    float hn = 0;
    float hm = 0;
    float hh = 0;
    float nh = 0;
    float nm = 0;
    float nn = 0;
    float mh = 0;
    float mn = 0;
    float mm = 0;
    float hn1 = 0;
    float hm1 = 0;
    float hh1 = 0;
    float nh1 = 0;
    float nm1 = 0;
    float nn1 = 0;
    float mh1 = 0;
    float mn1 = 0;
    float mm1 = 0;

    public List<TransitionMatrix> train(List<MessageData> traindata) {
        List<TransitionMatrix> result = new ArrayList<>();
        // for every line count  transitions in gunstig & ungunstig lines
        // add those two values inside result list as new transitionmatrix
        for(MessageData  messagedata : traindata){
            char currentPhase = messagedata.phase;
            List<Character> messwerten = messagedata.messwerte;
            // U h m n m h m m h n m n m h n n m n m n h
            for (int i = 0; i < messwerten.size() - 1; i++) {
                char currentMesswert = messwerten.get(i);
                char nextMesswert = messwerten.get(i + 1);
                if(currentPhase == 'U') {
                    if (currentMesswert == 'h' && nextMesswert == 'n') {
                        hn++;
                    } else if (currentMesswert == 'h' && nextMesswert == 'm') {
                        hm++;
                    } else if (currentMesswert == 'h' && nextMesswert == 'h') {
                        hh++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'm') {
                        nm++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'h') {
                        nh++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'n') {
                        nn++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'h') {
                        mh++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'n') {
                        mn++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'm') {
                        mm++;
                    }
                }

                if(currentPhase == 'G') {
                    if (currentMesswert == 'h' && nextMesswert == 'n') {
                        hn1++;
                    } else if (currentMesswert == 'h' && nextMesswert == 'm') {
                        hm1++;
                    } else if (currentMesswert == 'h' && nextMesswert == 'h') {
                        hh1++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'm') {
                        nm1++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'h') {
                        nh1++;
                    } else if (currentMesswert == 'n' && nextMesswert == 'n') {
                        nn1++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'h') {
                        mh1++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'n') {
                        mn1++;
                    } else if (currentMesswert == 'm' && nextMesswert == 'm') {
                        mm1++;
                    }
                }
            }
        }

        float prob_hn_ungunstig = hn / (hn+hn1);
        float prob_hm_ungunstig = hm / (hm+hm1);
        float prob_hh_ungunstig = hh / (hh+hh1);
        float prob_nh_ungunstig = nh / (nh+nh1);
        float prob_nm_ungunstig = nm / (nm+nm1);
        float prob_nn_ungunstig = nn / (nn+nn1);
        float prob_mn_ungunstig = mn / (mn+mn1);
        float prob_mh_ungunstig = mh / (mh+mh1);
        float prob_mm_ungunstig = mm / (mm+mm1);


        float prob_hm1_gunstig = hm1 / (hm+hm1);
        float prob_hn1_gunstig = hn1 / (hn+hn1);
        float prob_hh1_gunstig = hh1 / (hh+hh1);
        float prob_nh1_gunstig = nh1 / (nh+nh1);
        float prob_nm1_gunstig = nm1 / (nm+nm1);
        float prob_nn1_gunstig = nn1 / (nn+nn1);
        float prob_mn1_gunstig = mn1 / (mn+mn1);
        float prob_mh1_gunstig = mh1 / (mh+mh1);
        float prob_mm1_gunstig = mm1 / (mm+mm1);
        hnTM = new TransitionMatrix("hn", prob_hn_ungunstig, prob_hn1_gunstig);
        hmTM = new TransitionMatrix("hm", prob_hm_ungunstig, prob_hm1_gunstig);
        hhTM = new TransitionMatrix("hh", prob_hh_ungunstig, prob_hh1_gunstig);
        nhTM = new TransitionMatrix("nh", prob_nh_ungunstig, prob_nh1_gunstig);
        nmTM = new TransitionMatrix("nm", prob_nm_ungunstig, prob_nm1_gunstig);
        nnTM = new TransitionMatrix("nn", prob_nn_ungunstig, prob_nn1_gunstig);
        mhTM = new TransitionMatrix("mh", prob_mh_ungunstig, prob_mh1_gunstig);
        mnTM = new TransitionMatrix("mn", prob_mn_ungunstig, prob_mn1_gunstig);
        mmTM = new TransitionMatrix("mm", prob_mm_ungunstig, prob_mm1_gunstig);

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

    public float evaluate(List<TransitionMatrix> transitionMatrix, List<MessageData> evaldata) {
        int countPLUS20 = 0;
        int gewinn = 0;
        // sum gunstig transitionmatrix values
        // sum ungunstig transitionmatrix values
        // compare both and whichever is higher : choose that
        // gunstig oder ungunstig gefunden
        // gewinn calculate
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
                countPLUS20++;
                gewinn -= 12;
                System.out.println("calculatedPhase ist günstig und actualphase ist ungünstig: -12");
            } else if (calculatedPhase == 'U' && actualPhase == 'U') {
                gewinn -= 1;
                System.out.println("calculatedPhase ist ungünstig und actualphase ist ungünstig: -1 ");
            }

        }
        System.out.println("PLUS 20 N TIMES: " + countPLUS20);
        return gewinn;
    }
}
