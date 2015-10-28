package pl.agh.edu.evolution.crossovers;

import pl.agh.edu.evolution.genotypes.FloatGenotype;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bartosz
 *         Created on 2015-10-28.
 */
public class UniformCrossover extends AbstractCrossover<FloatGenotype> {

    private static final int SIZE = 100;

    private Double probability = 0.5d;

    public UniformCrossover() {
        super(SIZE);
    }

    public UniformCrossover(final int size, final Double probability) {
        super(size);
        this.probability = probability;
    }

    @Override
    protected FloatGenotype cross(final FloatGenotype parent1, final FloatGenotype parent2) {
        List<Double> combinedGenes = new ArrayList<>();
        for(int i=0; i<parent1.getGenes().size(); i++) {
            combinedGenes.add(getRng().nextDouble() < probability ? parent1.getGenes().get(i) : parent2.getGenes().get(i));
        }
        return new FloatGenotype(combinedGenes);
    }
}
