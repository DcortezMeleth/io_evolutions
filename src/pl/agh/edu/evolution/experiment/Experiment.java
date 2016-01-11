package pl.agh.edu.evolution.experiment;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.islands.IslandEvolution;
import org.uncommons.watchmaker.framework.islands.IslandEvolutionObserver;
import org.uncommons.watchmaker.framework.islands.RingMigration;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.RouletteWheelSelection;
import org.uncommons.watchmaker.framework.selection.SigmaScaling;
import org.uncommons.watchmaker.framework.selection.TournamentSelection;
import org.uncommons.watchmaker.framework.termination.GenerationCount;
import pl.agh.edu.evolution.crossovers.AverageFloatCrossover;
import pl.agh.edu.evolution.crossovers.SinglePointCrossover;
import pl.agh.edu.evolution.crossovers.UniformCrossover;
import pl.agh.edu.evolution.evaluation.FloatRastriginEvaluation;
import pl.agh.edu.evolution.evaluation.SchwefelEvaluation;
import pl.agh.edu.evolution.factory.FloatGenotypeFactory;
import pl.agh.edu.evolution.genotypes.FloatGenotype;
import pl.agh.edu.evolution.mutations.NormalMutation;
import pl.agh.edu.evolution.mutations.UniformFloatMutation;
import pl.agh.edu.evolution.observers.ConsoleIslandObserver;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Bartosz
 */
public class Experiment {

    public static void main(String[] args) {
        CandidateFactory<FloatGenotype> factory = new FloatGenotypeFactory();

        List<EvolutionaryOperator<FloatGenotype>> operators = new LinkedList<>();
        operators.add(new AverageFloatCrossover());
        operators.add(new UniformFloatMutation());

        EvolutionPipeline<FloatGenotype> pipeline = new EvolutionPipeline<>(operators);

        FitnessEvaluator<FloatGenotype> evaluator = new FloatRastriginEvaluation();

        Probability probability = new Probability(1.0);
        SelectionStrategy<Object> strategy = new TournamentSelection(probability);

        Random rng = new MersenneTwisterRNG();
        EvolutionEngine<FloatGenotype> engine =
                new GenerationalEvolutionEngine<>(factory, pipeline, evaluator, strategy, rng);

        List<EvolutionaryOperator<FloatGenotype>> operators2 = new LinkedList<>();
        operators2.add(new SinglePointCrossover());
        operators2.add(new UniformCrossover());
        operators2.add(new NormalMutation());

        EvolutionPipeline<FloatGenotype> pipeline2 = new EvolutionPipeline<>(operators2);

        FitnessEvaluator<FloatGenotype> evaluator2 = new SchwefelEvaluation();

        SelectionStrategy<Object> strategy2 = new RouletteWheelSelection();

        EvolutionEngine<FloatGenotype> engine2 =
                new GenerationalEvolutionEngine<>(factory, pipeline2, evaluator2, strategy2, rng);

        List<EvolutionaryOperator<FloatGenotype>> operators3 = new LinkedList<>();
        operators3.add(new UniformCrossover());
        operators3.add(new NormalMutation());

        EvolutionPipeline<FloatGenotype> pipeline3 = new EvolutionPipeline<>(operators3);

        FitnessEvaluator<FloatGenotype> evaluator3 = new FloatRastriginEvaluation();

        SelectionStrategy<Object> strategy3 = new SigmaScaling();

        EvolutionEngine<FloatGenotype> engine3 =
                new GenerationalEvolutionEngine<>(factory, pipeline3, evaluator3, strategy3, rng);

        List<EvolutionEngine<FloatGenotype>> islands = new ArrayList<>();
        islands.add(engine);
        islands.add(engine2);
        islands.add(engine3);

        IslandEvolution<FloatGenotype> islandEvolution =
                new IslandEvolution<>(islands, new RingMigration(), true, rng);

        islandEvolution.addEvolutionObserver(new ConsoleIslandObserver<>());

        islandEvolution.evolve(120, // Population size per island.
                5, // Elitism for each island.
                50, // Epoch length (no. generations).
                3, // Migrations from each island at each epoch.
                new GenerationCount(1000));
    }
}