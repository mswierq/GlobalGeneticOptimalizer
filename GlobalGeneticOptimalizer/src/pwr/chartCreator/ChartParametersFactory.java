package pwr.chartCreator;

import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Mapper;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.FuncMap;
import com.graphbuilder.math.VarMap;


public class ChartParametersFactory {

	public static Mapper getMapper(final Expression equation, final VarMap variablesMap, final FuncMap functionsMap){
		Mapper mapper = new Mapper() {
		       public double f(double x, double y) {
		    	   variablesMap.setValue(variablesMap.getVariableNames()[0], x);
		    	   variablesMap.setValue(variablesMap.getVariableNames()[1], y);
		           return equation.eval(variablesMap, functionsMap);
		        }
		     };
		return mapper;
	}

	public static Range getRange(double rangeFrom, double rangeTo) {
		return new Range(rangeFrom, rangeTo);
	}
}
