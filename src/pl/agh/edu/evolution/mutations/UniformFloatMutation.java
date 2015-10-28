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
    protected FloatGenotype mutate(final FloatGenotype genotype) {
        FloatGenotype result = new FloatGenotype(genotype);
        int index = getRng().nextInt(result.getGenes().size());
        Double updatedValue = result.getGenes().get(index) + uniform();
        result.getGenes().set(index, updatedValue);
        return result;
    }
}
