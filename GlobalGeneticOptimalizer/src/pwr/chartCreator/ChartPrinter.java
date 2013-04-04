package pwr.chartCreator;

import java.util.List;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.colors.Color;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.maths.Range;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.MultiColorScatter;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import pwr.parser.MeasurementData;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.FuncMap;
import com.graphbuilder.math.VarMap;

public class ChartPrinter {
	
	public static void printChart3D(List<MeasurementData> measurementData){
		int size = 100000;
		float xs;
		float y;
		float z;
		Coord3d[] points = new Coord3d[size];

		// Create scatter points
		for(int i=0; i<size; i++){
		    xs = (float)Math.random() - 0.5f;
		    y = (float)Math.random() - 0.5f;
		    z = (float)Math.random() - 0.5f;
		    points[i] = new Coord3d(xs, y, z);
		}       

		// Create a drawable scatter with a colormap
		MultiColorScatter scatter = new MultiColorScatter( points, new ColorMapper( new ColorMapRainbow(), -0.5f, 0.5f ) );

		// Create a chart and add scatter
		Chart chart = new Chart();
		chart.getAxeLayout().setMainColor(Color.WHITE);
		chart.getView().setBackgroundColor(Color.BLACK);
		chart.getScene().add(scatter);
		ChartLauncher.openChart(chart);
	}
	
	public static void printWaveFunction3D(){
		// Define a function to plot
		Mapper mapper = new Mapper() {
		    public double f(double x, double y) {
		        return 10 * Math.sin(x / 10) * Math.cos(y / 20) * x;
		    }
		};

		// Define range and precision for the function to plot
		Range range = new Range(-150, 150);
		int steps = 50;

		// Create a surface drawing that function
		Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper);
		surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
		surface.setFaceDisplayed(true);
		surface.setWireframeDisplayed(false);
		surface.setWireframeColor(Color.BLACK);

		// Create a chart and add the surface
		Chart chart = new Chart(Quality.Advanced);
		chart.getScene().getGraph().add(surface);
		ChartLauncher.openChart(chart);
	}
	
	public static void printGridFunction3D(final Expression equation, final VarMap variablesMap, final FuncMap functionsMap, String equationFormula, double rangeFrom, double rangeTo, double stepLength){
		
		 // Define a function to plot
	    Mapper mapper = new Mapper() {
	       public double f(double x, double y) {
	    	   variablesMap.setValue(variablesMap.getVariableNames()[0], x);
	    	   variablesMap.setValue(variablesMap.getVariableNames()[1], y);
	           return equation.eval(variablesMap, functionsMap);
	        }
	     };

	     // Define range and precision for the function to plot
	     Range range = new Range(rangeFrom, rangeTo);
	     int steps = 50;

	     // Create the object to represent the function over the given range.
	     final Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(range, steps, range, steps), mapper);
	     surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
	     surface.setFaceDisplayed(true);
	     surface.setWireframeDisplayed(true);
	     surface.setLegendDisplayed(true);
	     surface.setWireframeColor(Color.BLACK);

	     // Create a chart and add surface
	     Chart chart = new Chart(Quality.Advanced, "awt");
	     chart.getScene().getGraph().add(surface);
	     ChartLauncher.openChart(chart, equationFormula);
	}
	
	public static void printGridFunction3D(Mapper mapper, String equationFormula, Range rangeX, Range rangeY, double stepLength){
	     int steps = (int) ((rangeX.getMax()-rangeX.getMin()) > (rangeY.getMax()-rangeY.getMin()) ? (rangeX.getMax()-rangeX.getMin())/stepLength : 
	    	 																						(rangeY.getMax()-rangeY.getMin())/stepLength);  

	     // Create the object to represent the function over the given range.
	     final Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(rangeX, steps, rangeY, steps), mapper);
	     surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(), new Color(1, 1, 1, .5f)));
	     surface.setFaceDisplayed(true);
	     surface.setWireframeDisplayed(true);
	     surface.setLegendDisplayed(true);
	     surface.setWireframeColor(Color.BLACK);

	     // Create a chart and add surface
	     Chart chart = new Chart(Quality.Advanced, "awt");
	     chart.getScene().getGraph().add(surface);
	     ChartLauncher.openChart(chart, equationFormula);
	}
}
