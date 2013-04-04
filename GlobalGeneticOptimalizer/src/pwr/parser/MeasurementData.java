package pwr.parser;

public class MeasurementData {
	private Double x;
	private Double y;
	private Double z;
	
	public MeasurementData() {}

	public MeasurementData(Double x, Double y, Double z) {
		super();
		this.x = x;
		this.y = y;
		this.z = z;
	}

	//========= GENERATE GETTERS & SETTERS ==========//
	public Double getX() {
		return x;
	}

	public void setX(Double x) {
		this.x = x;
	}

	public Double getY() {
		return y;
	}

	public void setY(Double y) {
		this.y = y;
	}

	public Double getZ() {
		return z;
	}

	public void setZ(Double z) {
		this.z = z;
	}
}
