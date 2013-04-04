package pwr.algorithm;

import java.util.List;

import com.graphbuilder.math.VarMap;

public class Specimen implements Comparable<Specimen>{
	private Double score;
	private List<Double> chromosome;
	private Double rank;
	private Double distribution;
	
	public Specimen(List<Double> list) {
		super();
		this.chromosome = list;
	}

	public Double getScore() {
		return score;
	}

	public void setScore(Double score) {
		this.score = score;
	}

	public List<Double> getChromosome() {
		return chromosome;
	}

	public void setChromosome(List<Double> chromosome) {
		this.chromosome = chromosome;
	}
	
	public VarMap getVarMap() {
		VarMap varMap = new VarMap(false);
		varMap.setValue("pi", Math.PI);
		varMap.setValue("e", Math.E);
		for(int i = 0; i<chromosome.size(); i++) {
			varMap.setValue(EParameters.getEParameter(i), chromosome.get(i));
		}
        return varMap;
	}
	
	public Double getRank() {
		return rank;
	}

	public void setRank(Double rank) {
		this.rank = rank;
	}

	public Double getDistribution() {
		return distribution;
	}

	public void setDistribution(Double distribution) {
		this.distribution = distribution;
	}

	@Override
	public String toString() {
		return "Specimen [score=" + score + ", chromosome=" + chromosome + "]";
	}

	@Override
	public int compareTo(Specimen o) {
		return this.score.compareTo(o.getScore());
	}
}
