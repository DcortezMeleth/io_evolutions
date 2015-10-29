package pl.agh.edu.evolution.evaluation;

import org.uncommons.watchmaker.framework.FitnessEvaluator;
import pl.agh.edu.evolution.genotypes.PointGenotype;

import java.util.List;

/**
 * @author Bartosz
 *         Created on 2015-10-29.
 */
public class DeJongEvaluation implements FitnessEvaluator<PointGenotype> {

    @Override
    public double getFitness(final PointGenotype candidate, final List<? extends PointGenotype> population) {
        return Math.pow(candidate.getY(), 2) + Math.pow(candidate.getX(), 2);
    }

    @Override
    public boolean isNatural() {
        return true;
    }
}
