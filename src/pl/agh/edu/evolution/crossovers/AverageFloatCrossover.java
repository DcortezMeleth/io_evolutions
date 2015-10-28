package pl.agh.edu.evolution.crossovers;

import pl.agh.edu.evolution.genotypes.FloatGenotype;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bartosz
 *         Created on 2015-10-28.
 */
public class AverageFloatCrossover extends AbstractCrossover<FloatGenotype> {

    private static final int SIZE = 100;

    public AverageFloatCrossover() {
        super(SIZE);
    }

    public AverageFloatCrossover(final int size) {
        super(size);
    }

    @Override
    protected FloatGenotype cross(final FloatGenotype parent1, final FloatGenotype parent2) {
        List<Double> combinedGenes = new ArrayList<>();
        for(int i=0; i<parent1.getGenes().size(); i++) {
            combinedGenes.add((parent1.getGenes().get(i) + parent2.getGenes().get(i))/2);
        }
        return new FloatGenotype(combinedGenes);
    }
}
