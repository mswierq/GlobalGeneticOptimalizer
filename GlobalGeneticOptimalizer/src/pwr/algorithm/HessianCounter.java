package pwr.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javacalculus.core.CalculusEngine;
import pwr.gui.HessianDisplayer;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.ExpressionTree;

public class HessianCounter {

	public static void countHessian(Map<EParameters, Range> limits, Expression expression){
		ArrayList<Range> ranges = new ArrayList<Range>(limits.values());
		Expression[][] hessian = new Expression[ranges.size()][ranges.size()];
		List<Expression> firstDiffList = new ArrayList<Expression>(ranges.size());
		
		CalculusEngine calculusEngine = new CalculusEngine();
			calculusEngine.execute("syms x1, x2, x3, x4, x5");
		String formula = expression.toString().toUpperCase();
			formula=formula.replace("X1", "x1");
			formula=formula.replace("X2", "x2");
			formula=formula.replace("X3", "x3");
			formula=formula.replace("X4", "x4");
			formula=formula.replace("X5", "x5");
		System.out.println(formula);

		for(int i=0; i < ranges.size(); i++){
			firstDiffList.add(ExpressionTree.parse(calculusEngine.execute("DIFF(" + formula + "," + "x" + (i+1) + ")")));
			for(int j=0; j<ranges.size(); j++){
				hessian[i][j] = ExpressionTree.parse(countDerivative(calculusEngine, formula, i, j));
				System.out.println("Diff formula for: " + EParameters.getEParameter(i) + " , " +  EParameters.getEParameter(j) + ": " + hessian[i][j]);
			}
		}
		
		HessianResult result = new HessianResult(hessian, firstDiffList);
		new HessianDisplayer(result).main(result);
	}

	private static String countDerivative(CalculusEngine calculusEngine, String formula, int i, int j) {
		String diffFormula = calculusEngine.execute("DIFF(" + formula + "," + "x" + (i+1) + ")");
		return calculusEngine.execute("DIFF(" + diffFormula + "," + "x" + (j+1) + ")");
	}
}
