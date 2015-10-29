package pl.agh.edu.evolution.factory;

import pl.agh.edu.evolution.genotypes.PointGenotype;

import java.util.Random;

/**
 * @author Bartosz
 */
public class PointGenotypeFactory extends AbstractGenotypeFactory<PointGenotype> {

    private static final Double UPPERBOUND = 1.0d;

    private static final Double LOWERBOUND = 0.0d;

    public PointGenotypeFactory() {
        super(LOWERBOUND, UPPERBOUND);
    }

    public PointGenotypeFactory(final Double lowerbound, final Double upperbound) {
        super(lowerbound, upperbound);
    }

    @Override
    public PointGenotype generateRandomCandidate(final Random rng) {
        return new PointGenotype(uniform(rng), uniform(rng));
    }
}
