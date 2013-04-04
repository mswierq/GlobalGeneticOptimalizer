package pwr.algorithm;

public class Range {
	private Double max;
	private Double min;
	
	public Range(Double max, Double min) {
		super();
		
		if(min > max) {
			this.max = min;
			this.min = max;
		} else {	
			this.max = max;
			this.min = min;
		}
	}

	public Double getMax() {
		return max;
	}
	
	public void setMax(Double max) {
		this.max = max;
	}
	
	public Double getMin() {
		return min;
	}
	
	public void setMin(Double min) {
		this.min = min;
	}
	
	public Double scale(Double rand) {
		return (max-min)*rand + min;
	}

	public boolean contains(Double value) {
		return (value <= max && value >= min);
	}
}
