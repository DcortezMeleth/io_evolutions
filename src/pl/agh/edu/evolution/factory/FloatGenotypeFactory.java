package pl.agh.edu.evolution.factory;

import pl.agh.edu.evolution.genotypes.FloatGenotype;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Bartosz
 */
public class FloatGenotypeFactory extends AbstractGenotypeFactory<FloatGenotype> {

    private static final Double UPPERBOUND = 1.0d;

    private static final Double LOWERBOUND = 0.0d;

    private static final Integer DIM = 3;

    private Integer dim;

    public FloatGenotypeFactory() {
        super(LOWERBOUND, UPPERBOUND);
        this.dim = DIM;
    }

    public FloatGenotypeFactory(final Double lowerbound, final Double upperbound, final Integer dim) {
        super(lowerbound, upperbound);
        this.dim = dim;
    }

    @Override
    public FloatGenotype generateRandomCandidate(final Random rng) {
        List<Double> genes = new ArrayList<>();
        for(int i=0; i<dim; i++) {
            genes.add(uniform(rng));
        }
        return new FloatGenotype(genes);
    }
}
