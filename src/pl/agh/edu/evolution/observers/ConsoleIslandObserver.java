package pl.agh.edu.evolution.observers;

import org.uncommons.watchmaker.framework.PopulationData;
import org.uncommons.watchmaker.framework.islands.IslandEvolutionObserver;

/**
 * @author Bartosz
 *         Created on 2016-01-11.
 */
public class ConsoleIslandObserver<T> implements IslandEvolutionObserver<T> {

    @Override
    public void islandPopulationUpdate(int islandIndex, PopulationData<? extends T> data) {
        System.out.println("Island:" + islandIndex + "Generation: " + data.getGenerationNumber() + ", best candidate:" +
                " " + data.getBestCandidate() + ", with fitness: " + data.getBestCandidateFitness());
    }

    @Override
    public void populationUpdate(PopulationData<? extends T> data) {
        System.out.println("Generation: " + data.getGenerationNumber()
                + ", best candidate: " + data.getBestCandidate()
                + ", with fitness: " + data.getBestCandidateFitness());
    }
}
