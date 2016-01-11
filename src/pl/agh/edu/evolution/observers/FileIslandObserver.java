package pl.agh.edu.evolution.observers;

import org.uncommons.watchmaker.framework.PopulationData;
import org.uncommons.watchmaker.framework.islands.IslandEvolutionObserver;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Bartosz
 *         Created on 2016-01-11.
 */
public class FileIslandObserver<T> implements IslandEvolutionObserver<T> {

    List<FileWriter> islandPopulationWriters = new ArrayList<>();

    FileWriter populationWriter;

    public FileIslandObserver(final int islandsNo) {
        this("wholePopulation", "islandPopulation", islandsNo);
    }

    public FileIslandObserver(final String fileName, final String fileName2, final int islandsNo) {
        try {
            for(int i = 0; i < islandsNo; i++) {
                FileWriter fw = new FileWriter(fileName + String.valueOf(i));
                appendHeader(fw);
                islandPopulationWriters.add(fw);
            }
            populationWriter = new FileWriter(fileName2);
            appendHeader(populationWriter);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Dodaje naglowek do pliku csv
     */
    private void appendHeader(final FileWriter writer) throws IOException {
        writer.append("generation,population,bestCandidate,bestFitness,elapsedTime\n");
    }

    /**
     * Wypisuje dane do pliku csv
     */
    private void appendData(final FileWriter writer, final PopulationData<? extends T> data) {
        try {
            writer.append(String.valueOf(data.getGenerationNumber()));
            writer.append(",");
            writer.append(String.valueOf(data.getPopulationSize()));
            writer.append(",");
            writer.append(String.valueOf(data.getBestCandidate()));
            writer.append(",");
            writer.append(String.valueOf(data.getBestCandidateFitness()));
            writer.append(",");
            writer.append(String.valueOf(data.getElapsedTime()));
            writer.append("\n");
        } catch(IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void islandPopulationUpdate(final int islandIndex, final PopulationData<? extends T> data) {
        appendData(islandPopulationWriters.get(islandIndex), data);
    }

    @Override
    public void populationUpdate(final PopulationData<? extends T> data) {
        appendData(populationWriter, data);
    }
}
