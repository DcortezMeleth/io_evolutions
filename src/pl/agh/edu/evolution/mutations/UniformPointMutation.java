package pl.agh.edu.evolution.mutations;

import pl.agh.edu.evolution.genotypes.PointGenotype;

/**
 * @author Bartosz
 *         Created on 2015-10-28.
 */
public class UniformPointMutation extends AbstractMutation<PointGenotype> {

    public static final double RADIUS = 100.5d;

    public static final double PROBABILITY = 0.1d;

    public UniformPointMutation() {
        super(PROBABILITY, RADIUS);
    }

    public UniformPointMutation(final Double probability, final Double radius) {
        super(probability, radius);
    }

    @Override
    protected PointGenotype mutate(final PointGenotype genotype) {
        PointGenotype result = new PointGenotype(genotype);
        result.setX(result.getX() + uniform());
        result.setY(result.getY() + uniform());
        return result;
    }
}
