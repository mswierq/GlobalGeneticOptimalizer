package pwr.algorithm;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.SortedSet;
import java.util.TreeSet;

import pwr.algorithm.details.facade.ICrossOperator;
import pwr.algorithm.details.facade.IMutationOperator;
import pwr.algorithm.details.facade.ISelection;
import pwr.algorithm.details.impl.AlgorithmPicker;
import pwr.algorithm.details.impl.RankingSelection;
import pwr.algorithm.details.impl.ResultsGenerator;
import pwr.parser.FunctionMapBase;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.FuncMap;

public class GeneticAlgorithm {
	private ICrossOperator crossOperator;
	private IMutationOperator mutationOperator;
	private ISelection selection;
	private Map<EParameters, Range> limits;
	private ArrayList<Specimen> population;
	private ArrayList<Specimen> intermediatePopulation;
	private final Integer populationLimit;
	private Integer iterationsL;
	private Double propabilityCross;
	private Double propabilityMutation;
	private Random randomNumbersGenerator;
	private Expression expression;
	private Specimen bestMatch;
	private final Double maximumError;
	private Integer doneIterations;
	private ResultsGenerator results;
	private boolean enableStopCriterion;
	
	public GeneticAlgorithm(Map<EParameters, Range> limits, Integer iterationsL, Double propabilityCross, Double propabilityMutation, ECross crossAlgorithm, EMutation mutationAlgorithm, Expression expression) {
		super();
		this.limits = limits;
		this.iterationsL = iterationsL;
		this.propabilityCross = propabilityCross;
		this.propabilityMutation = propabilityMutation;
		this.populationLimit = 25;
		this.randomNumbersGenerator = new Random();
		this.expression = expression;
		this.selection = new RankingSelection(populationLimit);
		this.crossOperator = AlgorithmPicker.getCrossOperator(crossAlgorithm);
		this.mutationOperator = AlgorithmPicker.getMutationOperator(mutationAlgorithm, 
																	new ArrayList<Range>(limits.values()),
																	iterationsL,
																	expression);
		this.intermediatePopulation = new ArrayList<Specimen>();
		this.maximumError = 0.001;
		this.doneIterations = 0;
		this.enableStopCriterion = true;
		generateRandomPopulation();
		eval(population); //tymczasowo
		Collections.min(population);
		bestMatch = population.get(0); //tymczasowo
		
		this.results = new ResultsGenerator();
		String fileSep = System.getProperty("file.separator");
		this.results.clearMatchTrace();
		this.results.setMatchTraceSaveDirectory("matlab" + fileSep + "trace");
	}
	
	private List<Double> generateChromosome(SortedSet<EParameters> sortedVarSet){
		ArrayList<Double> chromosome = new ArrayList<Double>(limits.size());
		
		for(EParameters var : sortedVarSet)
			chromosome.add(limits.get(var).scale(randomNumbersGenerator.nextDouble()));
		
		return chromosome;
	}
	
	private void generateRandomPopulation() {
		this.population = new ArrayList<Specimen>(populationLimit);
		SortedSet<EParameters> sortedVarSet = new TreeSet<>();
		sortedVarSet.addAll(limits.keySet());
		
		for(int i = 0; i < populationLimit; i++) {	
			population.add(new Specimen(generateChromosome(sortedVarSet)));
		}
	}
	
	private void eval(ArrayList<Specimen> population) {
		FuncMap funcMap = new FunctionMapBase();
		for(Specimen s : population) {			
			s.setScore(expression.eval(s.getVarMap(), funcMap));
		}
	}
	
	private boolean checkStopCriteria() {
		Collections.sort(population);
		bestMatch = population.get(0);
		
		for(Specimen specimen: population) {
			if(specimen == bestMatch)
				continue;
			
			Double scoreError = Math.abs(bestMatch.getScore() - specimen.getScore());
			
			if(scoreError > maximumError)
				return false;
			
			for(int j = 0; j < specimen.getChromosome().size(); j++) {
				Double xError = Math.abs(bestMatch.getScore() - specimen.getScore());
				if(xError > maximumError)
					return false;
			}
		}
		return true;
	}
	
	private void replacePopulations() {
		eval(population);
		eval(intermediatePopulation);
		
		Collections.sort(population);
		Collections.sort(intermediatePopulation);
		
		for(int i = 0; i < intermediatePopulation.size(); i++) {
			if(population.get(populationLimit - i - 1).getScore() > intermediatePopulation.get(i).getScore())
				population.get(populationLimit - i - 1).setChromosome(intermediatePopulation.get(i).getChromosome());
			else
				break;
		}
		intermediatePopulation.clear();
	}

	public void execute() {
		for (int i = 0; i < iterationsL; i++) {
			doneIterations = i+1; // do debugowania (wiadomo, po którym wykonaniu pętli zadziałało kryterium stopu)
			intermediatePopulation = selection.select(population);
			crossOperator.cross(intermediatePopulation,propabilityCross);
			mutationOperator.mutate(intermediatePopulation,propabilityMutation,i);
			replacePopulations();
			eval(population);
			results.addMatchToTrace(bestMatch.getCopy());
			if(enableStopCriterion && checkStopCriteria()) {
				break;
			}
		}
		
		try {
			results.saveMatchTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Specimen getBestMatch() {
		return bestMatch;
	}
	
	public void enableStopCriterion(boolean enable) {
		this.enableStopCriterion = enable;
	}

	@Override
	public String toString() {
		String rs = "";
		
		for(Specimen s : population) {
			if(s != null)
				rs += (s + "\n");
			else
				rs += "null\n";
		}
		
		rs += "\ncurrent best match = " + bestMatch.getScore() + "; " + bestMatch + "\n";
		rs += "\nDone iterations = " + doneIterations + "\n";
		rs += "Stop criteria ON: " + this.enableStopCriterion + "\n";
		
		return rs;
	}
}
