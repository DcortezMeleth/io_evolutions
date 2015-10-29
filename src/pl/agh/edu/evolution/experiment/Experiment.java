package pl.agh.edu.evolution.experiment;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.TournamentSelection;
import org.uncommons.watchmaker.framework.termination.Stagnation;
import pl.agh.edu.evolution.crossovers.AverageCrossover;
import pl.agh.edu.evolution.evaluation.DeJongEvaluation;
import pl.agh.edu.evolution.factory.PointGenotypeFactory;
import pl.agh.edu.evolution.genotypes.PointGenotype;
import pl.agh.edu.evolution.mutations.UniformPointMutation;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Bartosz
 */
public class Experiment {

    public static void main(String[] args) {
        CandidateFactory<PointGenotype> factory = new PointGenotypeFactory();

        List<EvolutionaryOperator<PointGenotype>> operators = new LinkedList<>();
        operators.add(new AverageCrossover());
        operators.add(new UniformPointMutation());

        EvolutionPipeline<PointGenotype> pipeline = new EvolutionPipeline<>(operators);

        FitnessEvaluator<PointGenotype> evaluator = new DeJongEvaluation();

        Probability probability = new Probability(1.0);
        SelectionStrategy<Object> strategy = new TournamentSelection(probability);

        Random rng = new MersenneTwisterRNG();
        EvolutionEngine<PointGenotype> engine =
                new GenerationalEvolutionEngine<>(factory, pipeline, evaluator, strategy, rng);

        engine.addEvolutionObserver(new EvolutionObserver<PointGenotype>() {
            @Override
            public void populationUpdate(PopulationData<? extends PointGenotype> data) {
                System.out.println("Generation: " + data.getGenerationNumber()
                        + ", best candidate: " + data.getBestCandidate()
                        + ", with fitness: " + data.getBestCandidateFitness());
            }
        });

        engine.evolve(100, 0, new Stagnation(200, true));
    }

}
