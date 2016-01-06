package pl.agh.edu.evolution.mutations;

import pl.agh.edu.evolution.genotypes.FloatGenotype;

/**
 * @author Bartosz
 *         Created on 2015-10-28.
 */
public class NormalMutation extends AbstractMutation<FloatGenotype> {

    public static final double RADIUS = 0.1d;

    public static final double PROBABILITY = 0.01d;

    public NormalMutation() {
        super(PROBABILITY, RADIUS);
    }

    public NormalMutation(final Double probability, final Double radius) {
        super(probability, radius);
    }

    @Override
    protected FloatGenotype mutate(final FloatGenotype genotype) {
        FloatGenotype result = new FloatGenotype(genotype);
        for(int i=0; i<genotype.getGenes().size(); i++) {
            Double newValue = getRng().nextGaussian() * RADIUS + result.getGenes().get(i);
            result.getGenes().set(i, newValue);
        }
        return result;
    }
}
