package pl.agh.edu.evolution.crossovers;

import org.uncommons.watchmaker.framework.EvolutionaryOperator;
import pl.agh.edu.evolution.genotypes.AbstractGenotype;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * @author Bartosz
 *         Created on 2015-10-28.
 */
public abstract class AbstractCrossover<T extends AbstractGenotype> implements EvolutionaryOperator<T> {

    private Random rng = new Random();

    @Override
    public List<T> apply(final List<T> selectedCandidates, final Random rng) {
        List<T> crossedCandidates = new ArrayList<>();
        int size = selectedCandidates.size();
        for(int i = 0; i < size; i++) {
            T parent1 = selectedCandidates.get(rng.nextInt(size));
            T parent2 = selectedCandidates.get(rng.nextInt(size));
            T genotype = cross(parent1, parent2);
            crossedCandidates.add(genotype);
        }
        return crossedCandidates;
    }

    protected abstract T cross(T parent1, T parent2);

    protected Random getRng() {
        return rng;
    }
}
