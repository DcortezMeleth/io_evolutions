package pl.agh.edu.evolution.crossovers;

import pl.agh.edu.evolution.genotypes.FloatGenotype;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bartosz
 *         Created on 2015-10-28.
 */
public class UniformCrossover extends AbstractCrossover<FloatGenotype> {

    private Double probability = 0.5d;

    public UniformCrossover() {
    }

    public UniformCrossover(final Double probability) {
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
