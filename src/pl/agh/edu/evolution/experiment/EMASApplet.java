package pl.agh.edu.evolution.experiment;

import org.uncommons.maths.random.MersenneTwisterRNG;
import org.uncommons.maths.random.Probability;
import org.uncommons.swing.SwingBackgroundTask;
import org.uncommons.watchmaker.framework.*;
import org.uncommons.watchmaker.framework.islands.IslandEvolution;
import org.uncommons.watchmaker.framework.islands.RingMigration;
import org.uncommons.watchmaker.framework.operators.EvolutionPipeline;
import org.uncommons.watchmaker.framework.selection.TournamentSelection;
import org.uncommons.watchmaker.framework.termination.Stagnation;
import org.uncommons.watchmaker.swing.AbortControl;
import org.uncommons.watchmaker.swing.ProbabilityParameterControl;
import org.uncommons.watchmaker.swing.evolutionmonitor.EvolutionMonitor;
import pl.agh.edu.evolution.crossovers.AverageFloatCrossover;
import pl.agh.edu.evolution.crossovers.UniformCrossover;
import pl.agh.edu.evolution.evaluation.FloatRastriginEvaluation;
import pl.agh.edu.evolution.evaluation.SchwefelEvaluation;
import pl.agh.edu.evolution.factory.FloatGenotypeFactory;
import pl.agh.edu.evolution.genotypes.FloatGenotype;
import pl.agh.edu.evolution.mutations.NormalMutation;
import pl.agh.edu.evolution.mutations.UniformFloatMutation;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

/**
 * @author Bartosz
 *         Created on 2015-11-26.
 */
public class EMASApplet extends AbstractExampleApplet {

    private JSpinner populationSpinner;

    private JSpinner islandsSpinner;

    private JSpinner elitismSpinner;

    private JSpinner migrationsSpinner;

    private ProbabilityParameterControl selectionPressureControl;

    private AbortControl abortControl;

    private JButton startButton;

    private EvolutionMonitor<FloatGenotype> monitor;

    private JLabel populationLabel;

    private JLabel elitismLabel;

    public static void main(String[] args) throws IOException {
        EMASApplet gui = new EMASApplet();
        gui.displayInFrame("EMAS");
    }

    @Override
    protected void prepareGUI(final Container container) {
        JPanel controls = new JPanel(new BorderLayout());
        controls.add(createParametersPanel(), BorderLayout.NORTH);
        container.add(controls, BorderLayout.NORTH);

        monitor = new EvolutionMonitor<>(true);
        container.add(monitor.getGUIComponent(), BorderLayout.CENTER);
    }

    private Component createParametersPanel() {
        populationLabel = new JLabel("Population size per island: ");
        populationSpinner = new JSpinner(new SpinnerNumberModel(120, 2, 1000, 1));
        populationSpinner.setMaximumSize(populationSpinner.getMinimumSize());
        JLabel islandsLabel = new JLabel("Islands: ");
        islandsSpinner = new JSpinner(new SpinnerNumberModel(3, 1, 10, 1));
        islandsSpinner.setMaximumSize(islandsSpinner.getMinimumSize());
        elitismLabel = new JLabel("Elitism: ");
        elitismSpinner = new JSpinner(new SpinnerNumberModel(15, 1, 1000, 1));
        elitismSpinner.setMaximumSize(elitismSpinner.getMinimumSize());
        JLabel migrationsLabel = new JLabel("Migrations: ");
        migrationsSpinner = new JSpinner(new SpinnerNumberModel(20, 1, 1000, 1));
        migrationsSpinner.setMaximumSize(migrationsSpinner.getMinimumSize());
        JLabel selectionLabel = new JLabel("Selection pressure: ");
        selectionPressureControl = new ProbabilityParameterControl(Probability.EVENS, Probability.ONE, 2, new
                Probability(0.7));
        startButton = new JButton("Start");
        abortControl = new AbortControl();
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                abortControl.getControl().setEnabled(true);
                populationLabel.setEnabled(false);
                populationSpinner.setEnabled(false);
                elitismLabel.setEnabled(false);
                elitismSpinner.setEnabled(false);
                startButton.setEnabled(false);
                new EvolutionTask((Integer) populationSpinner.getValue(), (Integer) islandsSpinner.getValue(),
                        (Integer) elitismSpinner.getValue(), (Integer) migrationsSpinner.getValue(), abortControl
                        .getTerminationCondition(), new Stagnation(1000, false)).execute();
            }
        });
        abortControl.getControl().setEnabled(false);

        Box parameters = Box.createHorizontalBox();
        parameters.add(Box.createHorizontalStrut(10));
        parameters.add(populationLabel);
        parameters.add(Box.createHorizontalStrut(10));
        parameters.add(populationSpinner);
        parameters.add(Box.createHorizontalStrut(10));
        parameters.add(islandsLabel);
        parameters.add(Box.createHorizontalStrut(10));
        parameters.add(islandsSpinner);
        parameters.add(Box.createHorizontalStrut(10));
        parameters.add(elitismLabel);
        parameters.add(Box.createHorizontalStrut(10));
        parameters.add(elitismSpinner);
        parameters.add(Box.createHorizontalStrut(10));
        parameters.add(migrationsLabel);
        parameters.add(Box.createHorizontalStrut(10));
        parameters.add(migrationsSpinner);
        parameters.add(Box.createHorizontalStrut(10));
        parameters.add(selectionLabel);
        parameters.add(Box.createHorizontalStrut(10));
        parameters.add(selectionPressureControl.getControl());
        parameters.add(Box.createHorizontalStrut(10));
        parameters.add(startButton);
        parameters.add(abortControl.getControl());
        parameters.add(Box.createHorizontalStrut(10));

        parameters.setBorder(BorderFactory.createTitledBorder("Parameters"));
        return parameters;
    }


    /**
     * The task that acutally performs the evolution.
     */
    private class EvolutionTask extends SwingBackgroundTask<FloatGenotype> {
        private final int populationSize;
        private final int eliteCount;
        private final int islands;
        private final int migrations;
        private final TerminationCondition[] terminationConditions;


        EvolutionTask(int populationSize, int islands, int eliteCount, int migrations, TerminationCondition...
                terminationConditions) {
            this.populationSize = populationSize;
            this.islands = islands;
            this.migrations = migrations;
            this.eliteCount = eliteCount;
            this.terminationConditions = terminationConditions;
        }


        @Override
        protected FloatGenotype performTask() throws Exception {
            List<EvolutionEngine<FloatGenotype>> islands = new ArrayList<>();

            Random rng = new MersenneTwisterRNG();

            FitnessEvaluator<FloatGenotype> evaluator = new FloatRastriginEvaluation();
            for(int i = 0; i < this.islands; i++) {
                CandidateFactory<FloatGenotype> factory = new FloatGenotypeFactory(-5.12, 5.12, 20);

                List<EvolutionaryOperator<FloatGenotype>> operators = new LinkedList<>();
                operators.add(new AverageFloatCrossover());
                operators.add(new UniformFloatMutation());
                operators.add(new UniformCrossover());
                operators.add(new NormalMutation());

                EvolutionPipeline<FloatGenotype> pipeline = new EvolutionPipeline<>(operators);

                SelectionStrategy<Object> strategy = new TournamentSelection(selectionPressureControl
                        .getNumberGenerator());

                EvolutionEngine<FloatGenotype> engine = new GenerationalEvolutionEngine<>(factory, pipeline,
                        evaluator, strategy, rng);

                islands.add(engine);
            }


            IslandEvolution<FloatGenotype> islandEvolution = new IslandEvolution<>(islands, new RingMigration(),
                    evaluator.isNatural(), rng);
            islandEvolution.addEvolutionObserver(monitor);

            return islandEvolution.evolve(populationSize, // Population size per island.
                    eliteCount, // Elitism for each island.
                    50, // Epoch length (no. generations).
                    migrations, // Migrations from each island at each epoch.
                    terminationConditions);
        }


        @Override
        protected void postProcessing(FloatGenotype result) {
            abortControl.reset();
            abortControl.getControl().setEnabled(false);
            populationSpinner.setEnabled(true);
            elitismSpinner.setEnabled(true);
            startButton.setEnabled(true);
        }


        @Override
        protected void onError(Throwable throwable) {
            super.onError(throwable);
            postProcessing(null);
        }
    }
}
