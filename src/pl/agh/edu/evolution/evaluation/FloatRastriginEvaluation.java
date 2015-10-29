package pl.agh.edu.evolution.evaluation;

import org.uncommons.watchmaker.framework.FitnessEvaluator;
import pl.agh.edu.evolution.genotypes.FloatGenotype;

import java.util.List;

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
            sum += Math.pow(gene, 2) - A * Math.cos(2 * Math.PI * gene);
        }
        return sum;
    }

    @Override
    public boolean isNatural() {
        return true;
    }
}
