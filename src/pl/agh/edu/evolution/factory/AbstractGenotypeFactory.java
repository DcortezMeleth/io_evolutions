package pl.agh.edu.evolution.factory;

import org.uncommons.watchmaker.framework.factories.AbstractCandidateFactory;
import pl.agh.edu.evolution.genotypes.AbstractGenotype;

import java.util.Random;

/**
 * @author Bartosz
 */
public abstract class AbstractGenotypeFactory<T extends AbstractGenotype> extends AbstractCandidateFactory<T> {

    private Double lowerbound;

    private Double upperbound;

    public AbstractGenotypeFactory(final Double lowerbound, final Double upperbound) {
        this.lowerbound = lowerbound;
        this.upperbound = upperbound;
    }

    protected Double uniform(final Random rng) {
        return rng.nextDouble() * (this.upperbound - this.lowerbound) + this.lowerbound ;
    }
}
