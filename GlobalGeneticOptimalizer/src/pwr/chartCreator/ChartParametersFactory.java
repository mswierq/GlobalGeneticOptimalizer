package pwr.chartCreator;

import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Mapper;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.FuncMap;
import com.graphbuilder.math.VarMap;


public class ChartParametersFactory {
	
	private static String xName = "x1";
	private static String yName = "x2";

	public static Mapper getMapper(final Expression equation, final VarMap variablesMap){
		Mapper mapper = new Mapper() {
		       public double f(double x, double y) {
		    	   variablesMap.setValue(xName, x);
		    	   variablesMap.setValue(yName, y);
		    	   FuncMap map = new FuncMap(false);
		    	   map.loadDefaultFunctions();
		    	   return equation.eval(variablesMap, map);
		        }
		     };
		return mapper;
	}

	public static Range getRange(double rangeFrom, double rangeTo) {
		return new Range(rangeFrom, rangeTo);
	}
	
	public static void setXYVarNames(String xName, String yName) {
		ChartParametersFactory.xName = xName;
		ChartParametersFactory.yName = yName;
	}
}
