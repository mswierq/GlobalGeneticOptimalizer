package pwr.algorithm;

import java.util.HashMap;
import java.util.Map;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.ExpressionTree;

public class ConsoleTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Map<EParameters,Range> limity = new HashMap<EParameters,Range>();
		
		limity.put(EParameters.X1, new Range(2.0,-2.0));
		limity.put(EParameters.X2, new Range(2.0,-2.0));
		
		String testFunction = "x1^2+x2^2";
//		String testFunction = "x1*x2";rastrigin(x, y)
//		String testFunction = "goldsteinprice(x1, x2)";
		Expression equation = ExpressionTree.parse(testFunction);
		
		GeneticAlgorithm ga = new GeneticAlgorithm(limity, 10, 0.5, 0.5, ECross.arithmetic, EMutation.equal, equation);
		
		System.out.println(ga);
		System.out.println();
		
		ga.execute();
		
		System.out.println(ga);
	}

}
