package pwr.algorithm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Random;

import pwr.algorithm.details.facade.ICrossOperator;
import pwr.algorithm.details.facade.IMutationOperator;
import pwr.algorithm.details.facade.ISelection;
import pwr.algorithm.details.impl.AlgorithmPicker;
import pwr.algorithm.details.impl.RankingSelection;
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
	private Specimen firstBestMatch;
	private Specimen secondBestMatch;
	private final Double maximumError;
	private Integer doneIterations;
	
	public GeneticAlgorithm(Map<EParameters, Range> limits, Integer iterationsL, Double propabilityCross, Double propabilityMutation, ECross crossAlgorithm, EMutation mutationAlgorithm, Expression expression) {
		super();
		this.limits = limits;
		this.iterationsL = iterationsL;
		this.propabilityCross = propabilityCross;
		this.propabilityMutation = propabilityMutation;
		this.populationLimit = 100;
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
		generateRandomPopulation();
		eval(); //tymczasowo
		Collections.min(population);
		firstBestMatch = population.get(0); //tymczasowo
		secondBestMatch = population.get(1); //tymczasowo
	}
	
	private List<Double> generateChromosome(){
		ArrayList<Double> chromosome = new ArrayList<Double>(limits.size());
		
		for(Range limit : limits.values())
			chromosome.add(limit.scale(randomNumbersGenerator.nextDouble()));
		
		return chromosome;
	}
	
	private void generateRandomPopulation() {
		
		this.population = new ArrayList<Specimen>(populationLimit);
		for(int i = 0; i < populationLimit; i++) {			
			population.add(new Specimen(generateChromosome()));
		}
	}
	
	private void eval() {
		FuncMap funcMap = new FunctionMapBase();
		for(Specimen s : population) {			
			s.setScore(expression.eval(s.getVarMap(), funcMap));
		}
	}
	
	private boolean checkStopCriteria() {
		Collections.sort(population);
		firstBestMatch = population.get(0);
		secondBestMatch = population.get(1);
		Double scoreError = Math.abs(firstBestMatch.getScore() - secondBestMatch.getScore());
		
		if(scoreError > maximumError)
			return false;
		
		List<Double> firstChromosome = firstBestMatch.getChromosome();
		List<Double> secondChromosome = secondBestMatch.getChromosome();
		for(int i = 0; i < firstBestMatch.getChromosome().size(); i++) {
			Double xError = Math.abs(firstChromosome.get(i) - secondChromosome.get(i));
			if(xError > maximumError)
				return false;
		}
		return true;
	}
	
	private void replacePopulations() {
		Collections.sort(population);
		for(int i = 0; i > intermediatePopulation.size(); i++) {
			Specimen s = population.get(populationLimit - i - 1);
			s = intermediatePopulation.get(i);
		}
	}

	public void execute() {
		for (int i = 0; i < iterationsL; i++) {
			doneIterations = i+1; // do debugowania (wiadomo, po którym wykonaniu pętli zadziałało kryterium stopu)
			intermediatePopulation = selection.select(population);
			crossOperator.cross(intermediatePopulation,propabilityCross);
			mutationOperator.mutate(intermediatePopulation,propabilityMutation,i);
			replacePopulations();
			eval();
			if(checkStopCriteria()) {
				break;
			}
		}
	}

	public Specimen getBestMatch() {
		return firstBestMatch;
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
		
		rs += "\nbest match = " + firstBestMatch.getScore() + "; " + firstBestMatch + "\n";
		rs += "\nbest match = " + secondBestMatch.getScore() + "; " + secondBestMatch + "\n";
		rs += "\nDone iterations = " + doneIterations + "\n";
		
		return rs;
	}
}
