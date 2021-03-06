package pwr.algorithm.details.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javacalculus.core.CalculusEngine;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.ExpressionTree;

import pwr.algorithm.EParameters;
import pwr.algorithm.Range;
import pwr.algorithm.Specimen;
import pwr.algorithm.details.facade.IMutationOperator;
import pwr.parser.FunctionMapBase;

public class MutationGradient implements IMutationOperator{
	private Random randomNumbersGenerator;
	private List<Range> ranges;
	private Integer iterations;
	private Double bCoefficient;
	private Map<EParameters, Expression> diffMap = new HashMap<EParameters, Expression>();
	
	public MutationGradient(List<Range> ranges, Integer iterations, Expression expression) {
		this.ranges = ranges;
		this.randomNumbersGenerator = new Random();
		this.bCoefficient = 2.0;
		this.iterations = iterations;
		
		getDifferentialFormulaMap(expression);
	}

	private void getDifferentialFormulaMap(Expression expression) {
		CalculusEngine calculusEngine = new CalculusEngine();
			calculusEngine.execute("syms x1, x2, x3, x4, x5");
		String formula = expression.toString().toUpperCase();
			formula=formula.replace("X1", "x1");
			formula=formula.replace("X2", "x2");
			formula=formula.replace("X3", "x3");
			formula=formula.replace("X4", "x4");
			formula=formula.replace("X5", "x5");
		System.out.println(formula);

		for(int i=0; i < ranges.size(); i++){
			String diffFormula = calculusEngine.execute("DIFF(" + formula + "," + "x" + (i+1) + ")");
			System.out.println("Diff formula for: " + EParameters.getEParameter(i) + ": " + diffFormula);
			diffMap.put(EParameters.values()[i], ExpressionTree.parse(diffFormula));
		}
	}

	@Override
	public ArrayList<Specimen> mutate(ArrayList<Specimen> specimens, Double pm, Integer iteration) {
		for(Specimen specimen : specimens){
			if(randomNumbersGenerator.nextDouble() < pm)
				mutateSpecimen(specimen, iteration+1);
		}
		return specimens;
	}

	private void mutateSpecimen(Specimen specimen, Integer iteration) {
		double deltaFunctionValue;
		
		int chromosomeMutationId = (int) (ranges.size()*randomNumbersGenerator.nextDouble());
		Double chromosomeValue = specimen.getChromosome().get(chromosomeMutationId);
		
		if(randomNumbersGenerator.nextBoolean()){
			deltaFunctionValue = getDeltaFunctionValue(iteration, ranges.get(chromosomeMutationId).getMax()-chromosomeValue);
			specimen.getChromosome().set(chromosomeMutationId, 
										 chromosomeValue + deltaFunctionValue);
		}
		else{
			deltaFunctionValue = getDeltaFunctionValue(iteration, chromosomeValue - ranges.get(chromosomeMutationId).getMin());
			specimen.getChromosome().set(chromosomeMutationId, 
					 					 chromosomeValue - deltaFunctionValue);
		}
		
		gradientMutation(specimen, chromosomeMutationId, deltaFunctionValue);
	}

	private double getDeltaFunctionValue(Integer iteration, double range) {
		Double powerValue = Math.pow(1-(iteration.doubleValue()/this.iterations.doubleValue()),
				 					 this.bCoefficient);
		Double random = Math.pow(randomNumbersGenerator.nextDouble(),
								 powerValue);
		return range*(1-random);
	}
	
	private void gradientMutation(Specimen specimen, int chromosomeMutationId, double deltaFunctionValue) {
		double gamma = 0.8;
		double diffExpression = diffMap.get(EParameters.values()[chromosomeMutationId]).eval(specimen.getVarMap(), new FunctionMapBase());
		Double deltaValue = (gamma * (Math.abs(deltaFunctionValue)/Math.abs(diffExpression)))*diffExpression;
				
		specimen.getChromosome().set(chromosomeMutationId, 
									 specimen.getChromosome().get(chromosomeMutationId) - deltaValue);
	}
}
