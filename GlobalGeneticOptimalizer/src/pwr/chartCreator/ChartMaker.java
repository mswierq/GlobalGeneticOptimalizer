package pwr.chartCreator;

import org.jzy3d.chart.Chart;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import pwr.algorithm.EParameters;
import pwr.algorithm.Specimen;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.VarMap;

public class ChartMaker {

	public static void addPointToChart(Specimen specimen, Chart chart, EParameters x, EParameters y) {
		Point point = new Point(new Coord3d(specimen.getChromosome().get(x.ordinal()),
						  			        specimen.getChromosome().get(y.ordinal()),
						  			        specimen.getScore()),
						  					org.jzy3d.colors.Color.RED,
						  					7.5f);
		
		if( validatePoint(point, chart) )
			chart.getScene().getGraph().add(point);
	}
	
	public static void addBestPointToChart(Specimen specimen, Chart chart, EParameters x, EParameters y) {
		Point point = new Point(new Coord3d(specimen.getChromosome().get(x.ordinal()),
						  			        specimen.getChromosome().get(y.ordinal()),
						  			        specimen.getScore()),
						  					org.jzy3d.colors.Color.GREEN,
						  					15.0f);
		if(chart.getScene().getGraph().getBounds().contains(point.getBounds()))
			chart.getScene().getGraph().add(point);
	}
	
	public static Shape prepareSurface(Expression equation, VarMap variables, double rangeXFrom, double rangeXTo,
																double rangeYFrom, double rangeYTo, double stepLength) {
		Mapper mapper = ChartParametersFactory.getMapper(equation, variables);
		
		org.jzy3d.maths.Range rangeX = ChartParametersFactory.getRange(rangeXFrom, rangeXTo);
		org.jzy3d.maths.Range rangeY = ChartParametersFactory.getRange(rangeYFrom, rangeYTo);
		int stepsX = Math.round((float)(rangeX.getMax() - rangeX.getMin())/(float)stepLength);
		int stepsY = Math.round((float)(rangeY.getMax() - rangeY.getMin())/(float)stepLength);
		
		Shape surface = Builder.buildOrthonormal(new OrthonormalGrid(rangeX, stepsX, rangeY, stepsY), mapper);
		surface.setColorMapper(new ColorMapper(new ColorMapRainbow(), surface.getBounds().getZmin(), surface.getBounds().getZmax(),
											   new org.jzy3d.colors.Color(1, 1, 1, .5f)));
		surface.setWireframeDisplayed(true);
		surface.setWireframeColor(org.jzy3d.colors.Color.BLACK);
		
		return surface;
	}
	
	public static Chart prepareChart(Expression equation, VarMap variables, double rangeXFrom, double rangeXTo,
			double rangeYFrom, double rangeYTo, double stepLength) {
		final Chart chart = new Chart(Quality.Advanced);
		
		Shape surface = prepareSurface(equation, variables, rangeXFrom, rangeXTo, rangeYFrom, rangeYTo, stepLength);
		chart.getScene().getGraph().add(surface);
		
		return chart;
	}
	
	private static boolean validatePoint(Point point, Chart chart) {
		float xMax = chart.getScene().getGraph().getBounds().getXmax();
		float xMin = chart.getScene().getGraph().getBounds().getXmin();
		float yMax = chart.getScene().getGraph().getBounds().getYmax();
		float yMin = chart.getScene().getGraph().getBounds().getYmin();
		
		if(point.xyz.x >= xMin && point.xyz.x <= xMax && point.xyz.y >= yMin && point.xyz.y <= yMax)
			return true;
		
		return false;
	}

}
