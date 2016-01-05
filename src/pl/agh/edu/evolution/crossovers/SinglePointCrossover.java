package pl.agh.edu.evolution.crossovers;

import pl.agh.edu.evolution.genotypes.FloatGenotype;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Bartosz
 *         Created on 2015-10-28.
 */
public class SinglePointCrossover extends AbstractCrossover<FloatGenotype> {

    @Override
    protected FloatGenotype cross(final FloatGenotype parent1, final FloatGenotype parent2) {
        List<Double> combinedGenes = new ArrayList<>();
        int crossingPoint = getRng().nextInt(parent1.getGenes().size());
        combinedGenes.addAll(parent1.getGenes().subList(0, crossingPoint));
        combinedGenes.addAll(parent2.getGenes().subList(crossingPoint, parent2.getGenes().size()));
        return new FloatGenotype(combinedGenes);
    }
}
