package pl.agh.edu.evolution.evaluation;

import org.uncommons.watchmaker.framework.FitnessEvaluator;
import pl.agh.edu.evolution.genotypes.FloatGenotype;

import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.PI;

/**
 * @author Bartosz
 *         Created on 2015-10-29.
 */
public class FloatRastriginEvaluation implements FitnessEvaluator<FloatGenotype> {

    private static final int A = 10;

    @Override
    public double getFitness(final FloatGenotype candidate, final List<? extends FloatGenotype> population) {
        Double sum = (double) (candidate.getGenes().size() * A);
        for(Double gene : candidate.getGenes()) {
            sum += pow(gene, 2) - A * cos(2 * PI * gene);
        }
        return sum;
    }

    @Override
    public boolean isNatural() {
        return false;
    }
}
