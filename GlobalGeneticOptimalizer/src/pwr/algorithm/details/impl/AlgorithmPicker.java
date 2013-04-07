package pwr.algorithm.details.impl;

import java.util.List;

import com.graphbuilder.math.Expression;

import pwr.algorithm.ECross;
import pwr.algorithm.EMutation;
import pwr.algorithm.Range;
import pwr.algorithm.details.facade.ICrossOperator;
import pwr.algorithm.details.facade.IMutationOperator;

public class AlgorithmPicker {

	public static ICrossOperator getCrossOperator(ECross crossAlgorithm){
		switch(crossAlgorithm){
			case simple: return new CrossSimple();
			case arithmetic : return new CrossArithmetic(); 
			default: return null;
		}
	}
	
	public static IMutationOperator getMutationOperator(EMutation mutationAlgorithm, List<Range> ranges, Integer iterations, Expression expression){
		switch(mutationAlgorithm){
//		case equal: return new MutationEqual(ranges);
		case equal: return new MutationEqualAll(ranges);
		
//		case unequal: return new MutationUnequal(ranges, iterations);
		case unequal: return new MutationUnequalAll(ranges, iterations);
		
//		case gradient: return new MutationGradient(ranges, iterations, expression);
//		case gradient: return new MutationGradientWholeChromosome(ranges, iterations, expression);
		case gradient: return new MutationGradientAllGenes(ranges, iterations, expression);
		
		default: return null;
		}
	}
}
