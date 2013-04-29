package pwr.chartCreator;

import java.util.HashMap;

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
import org.jzy3d.plot3d.rendering.legends.colorbars.ColorbarLegend;

import pwr.algorithm.EParameters;
import pwr.algorithm.Specimen;
import pwr.parser.FunctionMapBase;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.VarMap;

public class ChartMaker {

	public static void addPointToChart(Specimen specimen, Chart chart, EParameters x1, EParameters x2) {
		Point point = new Point(new Coord3d(specimen.getChromosome().get(x1.ordinal()),
						  			        specimen.getChromosome().get(x2.ordinal()),
						  			        specimen.getScore()),
						  					org.jzy3d.colors.Color.RED,
						  					7.5f);
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
		surface.setFaceDisplayed(true);
		surface.setWireframeDisplayed(true);
		surface.setWireframeColor(org.jzy3d.colors.Color.BLACK);
		
		return surface;
	}
	
	public static Chart prepareChart(Expression equation, VarMap variables, double rangeXFrom, double rangeXTo,
			double rangeYFrom, double rangeYTo, double stepLength) {
		Chart chart = new Chart(Quality.Advanced);
		
		Shape surface = prepareSurface(equation, variables, rangeXFrom, rangeXTo, rangeYFrom, rangeYTo, stepLength);
		surface.setLegend(new ColorbarLegend(surface, chart.getView().getAxe().getLayout().getZTickProvider(),
													  chart.getView().getAxe().getLayout().getZTickRenderer()));
		chart.getScene().getGraph().add(surface);
		return chart;
	}

}
