package pwr.parser;

import com.graphbuilder.math.FuncMap;
import com.graphbuilder.math.func.Function;

public class FunctionMapBase extends FuncMap{

	public FunctionMapBase(){
		super(false);
		this.loadDefaultFunctions();
		this.setFunction("Rosenbrock", addRosenbrock());
		this.setFunction("Rastrigin", addRastrigin());
		this.setFunction("Ackley", addAckley());
		this.setFunction("GoldsteinPrice", addGoldsteinPrice());
		this.setFunction("Geem", addGeem());
	}

	private Function addRosenbrock() {
		return new Function() {
			
			@Override
			public double of(double[] param, int numParam) {
				double xSquare = Math.pow(param[0], 2);
				
				return Math.pow((1-param[0]),2)+100*Math.pow((param[1]-xSquare),2);
			}
			
			@Override
			public boolean acceptNumParam(int numParam) {
				return numParam == 2;
			}
		};
	}
	
	private Function addRastrigin() {
		return new Function() {
			
			@Override
			public double of(double[] param, int numParam) {
				double xSquare = Math.pow(param[0], 2);
				double ySquare = Math.pow(param[1], 2);
				
				return 20+xSquare-10*Math.cos(2*Math.PI*param[0]) + ySquare-10*Math.cos(2*Math.PI*param[1]);
			}
			
			@Override
			public boolean acceptNumParam(int numParam) {
				return numParam == 2;
			}
		};
	}
	
	private Function addAckley() {
		return new Function() {
			
			@Override
			public double of(double[] param, int numParam) {
				double xSquare = Math.pow(param[0], 2);
				double ySquare = Math.pow(param[1], 2);
				double firstPart = -0.2*Math.sqrt((xSquare+ySquare)/2);
				double secondPart = (Math.cos(2*Math.PI*param[0])+Math.cos(2*Math.PI*param[1]))/2;
				
//				-20*exp(-0.2*sqrt(1/2*(x^2+y^2)))-exp(1/2*(cos(2*pi*x)+cos(2*pi*y)))+20+exp(1)
//				(1+(x1+x2+1)^2*(19-14*x1+3*x1^2-14*x2+6*x1*x2+3*x2^2))*(30+(2*x1-3*x2)^2*(18-32*x1+12*x1^2+48*x2-36*x1*x2+27*x2^2)) //wersja dr Szlachcic
				return -20*Math.exp(firstPart)-Math.exp(secondPart) + 20 + Math.exp(1);
			}
			
			@Override
			public boolean acceptNumParam(int numParam) {
				return numParam == 2;
			}
		};
	}
	
	private Function addGoldsteinPrice() {
		return new Function() {
			
			@Override
			public double of(double[] param, int numParam) {
				double x = param[0];
				double y = param[1];
				double xSquare = Math.pow(param[0], 2);
				double ySquare = Math.pow(param[1], 2);
				
				return (1+Math.pow((x+y+1),2)*(19-14*x+3*xSquare-14*y+6*x*y+3*ySquare)) *
					   (30+Math.pow(2*x-3*y, 2)*(18-32*x+12*xSquare+48*y-36*x*y+27*ySquare));
			}
			
			@Override
			public boolean acceptNumParam(int numParam) {
				return numParam == 2;
			}
		};
	}
	
	private Function addGeem() {
		return new Function() {
			
			@Override
			public double of(double[] param, int numParam) {
				double x = param[0];
				double y = param[1];
				double xSquare = Math.pow(param[0], 2);
				double ySquare = Math.pow(param[1], 2);
				
				return (4*xSquare)-(2.1*Math.pow(x, 4))+(Math.pow(x, 6)/3)+(x*y)-(4*ySquare)+(4*Math.pow(y, 4));
			}
			
			@Override
			public boolean acceptNumParam(int numParam) {
				return numParam == 2;
			}
		};
	}
}
