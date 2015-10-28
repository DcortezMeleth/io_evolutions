package pl.agh.edu.evolution.crossovers;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import pl.agh.edu.evolution.genotypes.AbstractGenotype;

import java.util.List;
import java.util.Random;

/**
 * @author Bartosz
 *         Created on 2015-10-28.
 */
public abstract class AbstractCrossover<T extends AbstractGenotype> implements EvolutionaryOperator<T> {

    private Random rng = new Random();

    private int size;

    public AbstractCrossover(final int size) {
        this.size = size;
    }

    @Override
    public List<T> apply(final List<T> selectedCandidates, final Random rng) {
        for(int i = selectedCandidates.size(); i < this.size; i++) {
            T parent1 = selectedCandidates.get(rng.nextInt(selectedCandidates.size()));
            T parent2 = selectedCandidates.get(rng.nextInt(selectedCandidates.size()));
            T genotype = cross(parent1, parent2);
            selectedCandidates.add(genotype);
        }
        return selectedCandidates;
    }

    protected abstract T cross(T parent1, T parent2);

    protected Random getRng() {
        return rng;
    }
}
