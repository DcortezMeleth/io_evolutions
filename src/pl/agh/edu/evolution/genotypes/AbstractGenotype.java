package pl.agh.edu.evolution.genotypes;

/**
 * @author Bartosz
 *         Created on 2015-10-28.
 */
public abstract class AbstractGenotype {

    private Double fitness = null;

    public Double getFitness() {
        return fitness;
    }

    public void setFitness(final Double fitness) {
        this.fitness = fitness;
    }
}
