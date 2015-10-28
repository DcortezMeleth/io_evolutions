package pl.agh.edu.evolution.mutations;

import pl.agh.edu.evolution.genotypes.FloatGenotype;

/**
 * @author Bartosz
 *         Created on 2015-10-28.
 */
public class UniformFloatMutation extends AbstractMutation<FloatGenotype> {

    public static final double RADIUS = 0.5d;

    public static final double PROBABILITY = 0.1d;

    public UniformFloatMutation() {
        super(PROBABILITY, RADIUS);
    }

    public UniformFloatMutation(final Double probability, final Double radius) {
        super(probability, radius);
    }

    @Override
    protected void mutate(final FloatGenotype genotype) {
        int index = getRng().nextInt(genotype.getGenes().size());
        Double updatedValue = genotype.getGenes().get(index) + uniform();
        genotype.getGenes().set(index, updatedValue);
    }
}
