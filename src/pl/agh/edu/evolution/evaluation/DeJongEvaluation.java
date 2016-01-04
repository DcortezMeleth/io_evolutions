package pl.agh.edu.evolution.evaluation;

import org.uncommons.watchmaker.framework.FitnessEvaluator;
import pl.agh.edu.evolution.genotypes.PointGenotype;

import java.util.List;

import static java.lang.Math.pow;

/**
 * De Jong's function 1
 *
 * @author Bartosz
 *         Created on 2015-10-29.
 */
public class DeJongEvaluation implements FitnessEvaluator<PointGenotype> {

    @Override
    public double getFitness(final PointGenotype candidate, final List<? extends PointGenotype> population) {
        return pow(candidate.getY(), 2) + pow(candidate.getX(), 2);
    }

    @Override
    public boolean isNatural() {
        return false;
    }
}
