package pwr.algorithm.details.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pwr.algorithm.Range;
import pwr.algorithm.Specimen;
import pwr.algorithm.details.facade.IMutationOperator;

public class MutationUnequal implements IMutationOperator{
	private Random randomNumbersGenerator;
	private List<Range> ranges;
	private Integer iterations;
	private Double bCoefficient;
	
	public MutationUnequal(List<Range> ranges, Integer iterations) {
		this.ranges = ranges;
		this.randomNumbersGenerator = new Random();
		this.iterations = iterations;
		this.bCoefficient = 2.0;
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
	}

	private double getDeltaFunctionValue(Integer iteration, double range) {
		Double powerValue = Math.pow(1-(iteration.doubleValue()/this.iterations.doubleValue()),
				 					 this.bCoefficient);
		Double random = Math.pow(randomNumbersGenerator.nextDouble(),
								 powerValue);
		return range*(1-random);
	}

	
}
