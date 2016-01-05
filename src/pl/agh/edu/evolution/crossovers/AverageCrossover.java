package pl.agh.edu.evolution.crossovers;

import pl.agh.edu.evolution.genotypes.PointGenotype;

/**
 * @author Bartosz
 *         Created on 2015-10-28.
 */
public class AverageCrossover extends AbstractCrossover<PointGenotype> {

    @Override
    protected PointGenotype cross(final PointGenotype parent1, final PointGenotype parent2) {
        return new PointGenotype((parent1.getX() + parent2.getX())/2, (parent1.getY() + parent2.getY())/2);
    }
}
