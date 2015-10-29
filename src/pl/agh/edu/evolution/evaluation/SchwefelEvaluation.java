package pl.agh.edu.evolution.evaluation;

import org.uncommons.watchmaker.framework.FitnessEvaluator;
import pl.agh.edu.evolution.genotypes.FloatGenotype;

import java.util.List;

/**
 * @author Bartosz
 *         Created on 2015-10-29.
 */
public class SchwefelEvaluation implements FitnessEvaluator<FloatGenotype> {

    private static final double X = 418.9829;

    @Override
    public double getFitness(final FloatGenotype candidate, final List<? extends FloatGenotype> population) {
        Double sum = candidate.getGenes().size() * X;
        for(Double gene : candidate.getGenes()) {
            sum += Math.sin(Math.sqrt(Math.abs(gene))) - gene;
        }
        return sum;
    }

    @Override
    public boolean isNatural() {
        return true;
    }
}
