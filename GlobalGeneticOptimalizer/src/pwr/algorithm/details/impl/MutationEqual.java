package pwr.algorithm.details.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import pwr.algorithm.Range;
import pwr.algorithm.Specimen;
import pwr.algorithm.details.facade.IMutationOperator;

public class MutationEqual implements IMutationOperator {
	private Random randomNumbersGenerator;
	private List<Range> ranges;
	
	public MutationEqual(List<Range> ranges) {
		randomNumbersGenerator = new Random();
		this.ranges = ranges;
	}
	
	@Override
	public ArrayList<Specimen> mutate(ArrayList<Specimen> specimens, Double pm, Integer iteration) {	
		for(Specimen specimen : specimens){
			if(randomNumbersGenerator.nextDouble() < pm)
				mutateSpecimen(specimen);
		}
		return specimens;
	}

	private void mutateSpecimen(Specimen specimen) {
		int chromosomeMutationId = (int) (ranges.size()*randomNumbersGenerator.nextDouble());
		specimen.getChromosome().set(chromosomeMutationId, 
									 ranges.get(chromosomeMutationId).scale(randomNumbersGenerator.nextDouble()));
	}

}
