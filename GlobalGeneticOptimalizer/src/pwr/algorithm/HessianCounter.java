package pwr.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javacalculus.core.CalculusEngine;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.ExpressionTree;
import com.graphbuilder.math.FuncMap;

public class HessianCounter {

	public static HessianResult countHessian(Map<EParameters, Range> limits, Expression expression){
		ArrayList<Range> ranges = new ArrayList<Range>(limits.values());
		String[][] hessian = new String[ranges.size()][ranges.size()];
		List<String> firstDiffList = new ArrayList<String>(ranges.size());
		
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
//			firstDiffList.add(ExpressionTree.parse(calculusEngine.execute("DIFF(" + formula + "," + "x" + (i+1) + ")")));
			firstDiffList.add(calculusEngine.execute("DIFF(" + formula + "," + "x" + (i+1) + ")"));
			for(int j=0; j<ranges.size(); j++){
//				hessian[i][j] = ExpressionTree.parse(countDerivative(calculusEngine, formula, i, j));
				hessian[i][j] = countDerivative(calculusEngine, formula, i, j);
				System.out.println("Diff formula for: " + EParameters.getEParameter(i) + " , " +  EParameters.getEParameter(j) + ": " + hessian[i][j]);
			}
		}
		
		HessianResult result = new HessianResult(hessian, firstDiffList);
		return result;
	}

	private static String countDerivative(CalculusEngine calculusEngine, String formula, int i, int j) {
		String diffFormula = calculusEngine.execute("DIFF(" + formula + "," + "x" + (i+1) + ")");
		return calculusEngine.execute("DIFF(" + diffFormula + "," + "x" + (j+1) + ")");
	}

	public static HessianResult countHessian(Map<EParameters, Range> limits, Expression expression, Specimen bestMatch) {
		HessianResult result = countHessian(limits, expression);
		FuncMap functions = new FuncMap(false);
		functions.loadDefaultFunctions();
		
		for(int i=0; i < result.getHessian().length; i++){
			result.getFirstDiffList().set(i, "" + ExpressionTree.parse(result.getFirstDiffList().get(i)).eval(bestMatch.getVarMap(), functions));
			for(int j=0; j < result.getHessian().length; j++){
				result.getHessian()[i][j]="" + ExpressionTree.parse(result.getHessian()[i][j]).eval(bestMatch.getVarMap(), functions);
			}
		}
			
		return result;
	}
}
