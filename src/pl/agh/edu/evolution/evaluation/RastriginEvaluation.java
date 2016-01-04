package pl.agh.edu.evolution.evaluation;

import org.uncommons.watchmaker.framework.FitnessEvaluator;
import pl.agh.edu.evolution.genotypes.PointGenotype;

import java.util.List;

import static java.lang.Math.cos;
import static java.lang.Math.pow;
import static java.lang.Math.PI;

/**
 * @author Bartosz
 *         Created on 2015-10-29.
 */
public class RastriginEvaluation implements FitnessEvaluator<PointGenotype> {

    private static final int A = 10;

    @Override
    public double getFitness(final PointGenotype candidate, final List<? extends PointGenotype> population) {
        return 2 * A + pow(candidate.getX(), 2) - A * cos(2 * PI * candidate.getX())
                + pow(candidate.getY(), 2) - A * cos(2 * PI * candidate.getY());
    }

    @Override
    public boolean isNatural() {
        return false;
    }
}
