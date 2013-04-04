package pwr.algorithm.details.facade;

import java.util.ArrayList;

import pwr.algorithm.Specimen;

public interface IMutationOperator {
	public ArrayList<Specimen> mutate(ArrayList<Specimen> specimens, Double pm, Integer i);
}
