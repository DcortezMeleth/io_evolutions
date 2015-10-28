package pl.agh.edu.evolution.genotypes;

import java.util.List;

/**
 * @author Bartosz
 *         Created on 2015-10-28.
 */
public class FloatGenotype extends AbstractGenotype {

    private List<Double> genes;

    public FloatGenotype(final List<Double> genes) {
        super();
        this.genes = genes;
    }

    public List<Double> getGenes() {
        return genes;
    }

    public void setGenes(final List<Double> genes) {
        this.genes = genes;
    }

    @Override
    public String toString() {
        String result = "[";
        for(Double f : getGenes()) {
            result += f + " ";
        }
        return result + "], f:" + getFitness();
    }
}
