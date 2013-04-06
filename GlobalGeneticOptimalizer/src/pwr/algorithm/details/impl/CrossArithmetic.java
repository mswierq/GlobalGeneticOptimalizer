package pwr.algorithm.details.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import pwr.algorithm.Specimen;
import pwr.algorithm.details.facade.ICrossOperator;

public class CrossArithmetic implements ICrossOperator {

	private ArrayList<Specimen> choosenSpecimens;
	private Random randomNumbersGenerator;
	
	public CrossArithmetic() {
		choosenSpecimens = new ArrayList<Specimen>();
		this.randomNumbersGenerator = new Random();
	}
	
	@Override
	public void cross(ArrayList<Specimen> specimens, Double pc) {
		choosenSpecimens = new ArrayList<Specimen>();
		Iterator<Specimen> sItr = specimens.iterator();
		while(sItr.hasNext()){
			Specimen s = sItr.next();
			Double r = randomNumbersGenerator.nextDouble();
			if(r < pc) {
				choosenSpecimens.add(s);
			}
		}
		
		specimens.clear();
		
		if(choosenSpecimens.size() % 2 != 0) {
			specimens.add(choosenSpecimens.get(choosenSpecimens.size()-1));
			choosenSpecimens.remove(choosenSpecimens.size()-1);
		}
		
		while(choosenSpecimens.size() > 0) {
			int csSize = choosenSpecimens.size();
			Specimen specimenA = choosenSpecimens.get((int)Math.round((csSize-1)*randomNumbersGenerator.nextDouble()));
			choosenSpecimens.remove(specimenA);
			Specimen specimenB = choosenSpecimens.get((int)Math.round((csSize-2)*randomNumbersGenerator.nextDouble()));
			choosenSpecimens.remove(specimenB);
			crossPair(specimenA,specimenB);
			specimens.add(specimenA);
			specimens.add(specimenB);
		}
	}

	private void crossPair(Specimen specimenA, Specimen specimenB) {
		int chSize = specimenA.getChromosome().size();
		ArrayList<Double> newChromosomeA = new ArrayList<Double>(chSize);
		ArrayList<Double> newChromosomeB = new ArrayList<Double>(chSize);
		Double a = randomNumbersGenerator.nextDouble();
		for(int i = 0; i < chSize; i++) {
			newChromosomeA.add(specimenA.getChromosome().get(i)*a + specimenB.getChromosome().get(i)*(1-a));
			newChromosomeB.add(specimenB.getChromosome().get(i)*a + specimenA.getChromosome().get(i)*(1-a));
		}
		specimenA.setChromosome(newChromosomeA);
		specimenB.setChromosome(newChromosomeB);
	}
}
