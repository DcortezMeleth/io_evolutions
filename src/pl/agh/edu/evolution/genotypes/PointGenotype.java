package pl.agh.edu.evolution.genotypes;

/**
 * @author Bartosz
 *         Created on 2015-10-28.
 */
public class PointGenotype extends AbstractGenotype {

    private Double x;

    private Double y;

    PointGenotype(final Double x, final Double y) {
        super();
        this.x = x;
        this.y = y;
    }

    public Double getX() {
        return x;
    }

    public void setX(final Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(final Double y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + getX() + ", " + getY() + "), f:" + getFitness();
    }
}
