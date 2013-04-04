package pwr.algorithm.details.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import pwr.algorithm.Specimen;
import pwr.algorithm.details.facade.ISelection;

public class RankingSelection implements ISelection {

	private int rankSum;
	private Random randomNumbersGenerator;
	
	public RankingSelection(int popCount) {
		this.rankSum = 0;
		this.randomNumbersGenerator = new Random();
		
		for(int i = 1; i <= popCount; i++) {
			this.rankSum += i;
		}
	}
	
	@Override
	public ArrayList<Specimen> select(ArrayList<Specimen> specimens) {
		Collections.sort(specimens);
		Collections.reverse(specimens);
		
		for(int i = 0; i < specimens.size(); i++) {
			specimens.get(i).setRank((double)(i+1)/(double)rankSum);
		}
		
		Double dist = 0.0;
		
		for(int i = 0; i < specimens.size(); i++) {
			if(i > 0)
				dist = specimens.get(i-1).getRank();
			specimens.get(i).setRank(specimens.get(i).getRank() + dist);
		}
		
		ArrayList<Specimen> newGeneration = new ArrayList<Specimen>();
		
		while(newGeneration.size() < specimens.size()) {
			Double randomRank = this.randomNumbersGenerator.nextDouble();
			for(Specimen s: specimens) {
				if(s.getRank() < randomRank && newGeneration.size() < specimens.size()) {
					newGeneration.add(s);
				} else if(newGeneration.size() < specimens.size()) {
					break;
				}
			}
		}
		
		return newGeneration;
	}

}
