package pl.agh.edu.evolution.mutations;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import pl.agh.edu.evolution.genotypes.AbstractGenotype;

import java.util.List;
import java.util.Random;

/**
 * @author Bartosz
 *         Created on 2015-10-28.
 */
public abstract class AbstractMutation<T extends AbstractGenotype> implements EvolutionaryOperator<T> {

    private Double probability;

    private Double radius;

    private Random rng = new Random();

    public AbstractMutation(final Double probability, final Double radius) {
        this.probability = probability;
        this.radius = radius;
    }

    @Override
    public List<T> apply(final List<T> selectedCandidates, final Random rng) {
        for(T genotype : selectedCandidates) {
            if(shouldMutate()) {
                mutate(genotype);
            }
        }

        return selectedCandidates;
    }

    protected boolean shouldMutate() {
        return rng.nextDouble() < this.probability;
    }

    protected abstract void mutate(T genotype);

    protected Double uniform() {
        return this.rng.nextDouble() * 2 * this.radius - this.radius;
    }

    public Random getRng() {
        return rng;
    }
}
