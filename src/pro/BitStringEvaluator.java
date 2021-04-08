package pro;

import java.util.List;
import org.uncommons.maths.binary.BitString;
import org.uncommons.watchmaker.framework.FitnessEvaluator;

public class BitStringEvaluator implements FitnessEvaluator<BitString>{

    @Override
    public double getFitness(BitString candidate, List<? extends BitString> population)
    {
        double range=30;
        double distance;
        int totalScore=0;

        String degerler = candidate.toString();
        if(candidate.countSetBits()==50){
            for(int i=0;i<Coordinate.customer_coords.length;i++) {
                for (int j = 0; j < Coordinate.station_coords.length; j++) {

                    if (degerler.charAt(j) == '1') {
                        double x1 = (double) Coordinate.customer_coords[i][0];
                        double y1 = (double) Coordinate.customer_coords[i][1];
                        double x2 = (double) Coordinate.station_coords[j][0];
                        double y2 = (double) Coordinate.station_coords[j][1];
                        distance = Math.sqrt(Math.pow(x2 - x1, 2)+Math.pow(y2 - y1, 2));

                        if (distance <= range) {
                            totalScore++;
                            break; //kapsanani bir daha saymamasi icin
                        }
                    }
                }
            }

        }
        return totalScore;//kapsanan musteri sayisi

    }

    public boolean isNatural()
    {
        return true;
    } //maksimizasyon
}
