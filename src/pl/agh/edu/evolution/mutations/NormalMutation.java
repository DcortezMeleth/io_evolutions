package pl.agh.edu.evolution.mutations;

import pl.agh.edu.evolution.genotypes.FloatGenotype;

/**
 * @author Bartosz
 *         Created on 2015-10-28.
 */
public class NormalMutation extends AbstractMutation<FloatGenotype> {

    public static final double RADIUS = 0.1d;

    public static final double PROBABILITY = 0.01d;

    public static final double MEAN = 100.0d;
    
    public static final double VARIANCE = 5.0d;

    public NormalMutation() {
        super(PROBABILITY, RADIUS);
    }

    public NormalMutation(final Double probability, final Double radius) {
        super(probability, radius);
    }

    @Override
    protected boolean shouldMutate() {
        return true;
    }

    @Override
    protected void mutate(final FloatGenotype genotype) {
        for(int i=0; i<genotype.getGenes().size(); i++) {
            if(super.shouldMutate()) {
                Double newValue = getRng().nextGaussian() * VARIANCE + MEAN;
                genotype.getGenes().set(i, newValue);
            }
        }
    }
}
