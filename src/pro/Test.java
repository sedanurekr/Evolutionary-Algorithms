package pro;


import java.util.Arrays;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.uncommons.maths.binary.BitString;
import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.examples.EvolutionLogger;
import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import org.uncommons.watchmaker.framework.GenerationalEvolutionEngine;
import org.uncommons.watchmaker.framework.factories.BitStringFactory;
import org.uncommons.watchmaker.framework.operators.BitStringCrossover;
import org.uncommons.watchmaker.framework.operators.BitStringMutation;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RankSelection;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.selection.StochasticUniversalSampling;
import org.uncommons.watchmaker.framework.termination.GenerationCount;
import org.uncommons.watchmaker.framework.termination.TargetFitness;

public class Test {
    public static void main(String[] args) {

        Coordinate c = new Coordinate();

        c.randomGenerator(400, 400, 250, 100);

        int length = 100;
        int SEED = 2020;
        int MAX_ITER = 1000;
        BitStringEvaluator fitnessFunction = new BitStringEvaluator();
        List<EvolutionaryOperator<BitString>> operators = new ArrayList<EvolutionaryOperator<BitString>>(2);
        operators.add(new BitStringCrossover(2, new Probability(0.7d)));
        operators.add(new BitStringMutation(new Probability(0.01d)));
        EvolutionaryOperator<BitString> pipeline = new EvolutionPipeline<BitString>(operators);

        double[] results = new double[25];
        for (int run = 0; run < 25; run++) {  //farkli olasiliksal sonuclarin ortalasini almak icin 25 kez calisiyor
            GenerationalEvolutionEngine<BitString> engine
                    = new GenerationalEvolutionEngine<BitString>(new BitStringFactory(length), pipeline,
                    fitnessFunction, //uygunluk fonksiyonu
                    new RouletteWheelSelection(),
                    new Random(run));  //SEED degeri olarak run verdik, artik calistiginda ayni degerleri verecek
            //SEED kullanmak deneyi tekrar edebilmemizi saglar
            engine.setSingleThreaded(true); // Performs better for very trivial fitness evaluations.
            //engine.addEvolutionObserver(new EvolutionLogger<BitString>());
            BitString result = engine.evolve(40, //individuals in each generation.
                    1, // Elitizm sayısı
                    new GenerationCount(MAX_ITER)); // Sonlanma koşulu
            System.out.println("Run " + run + ", Best:" + result);
            results[run] = fitnessFunction.getFitness(result, null); //ekrana yazdirmak yerine burada saklayip sonra ortalamasini alacagiz
            //En iyi uygunluk degerini yazdırmak icin
            System.out.println("Run " + run + ", Best Uygunluk: " + fitnessFunction.getFitness(result, null));
            //popülasyonu yazdırmadan sadece besti yazdırmak istediğimiz için diğer parametreye null dedik

        }

        //ISTATISTIKSEL VERILER

        double bestF = 0, worstF = 0, totalF = 0, mean, median;
        for (int run = 0; run < 25; run ++) {
            if (run == 0) {
                bestF = results[run];
                worstF = results[run];
            } else {
                if (results[run] > bestF) {
                    bestF = results[run];
                }

                if (results[run] < worstF) {
                    worstF = results[run];
                }

            }
            totalF += results[run];
        }
        mean = totalF / 25;

        //Standart Sapma
        double sapma = 0;
        for(int i=0; i<25; i++){
            sapma += (Math.pow((results[i] - mean), 2));
        }
        double karelerToplamiOrtalamasi = (sapma) / (results.length);
        double standartSapma = (Math.sqrt(karelerToplamiOrtalamasi));

        //Median
        Arrays.sort(results);
        if (results.length % 2 == 0)
            median = ((double)results[results.length/2] + (double)results[results.length/2 - 1])/2;
        else
            median = (double) results[results.length/2];



        System.out.println("En İyi Uygunluk: " + bestF);
        System.out.println("En Kötü Uygunluk: " + worstF);
        System.out.println("Ortalama Uygunluk: " + mean);
        System.out.println("Medyan uygunluk değeri: " + median);
        System.out.println("Uygunluk değerlerinin standart sapması: " + standartSapma);


    }
}
