package pwr.chartCreator;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.controllers.mouse.picking.MousePickingController;
import org.jzy3d.colors.ColorMapper;
import org.jzy3d.colors.colormaps.ColorMapRainbow;
import org.jzy3d.maths.Coord3d;
import org.jzy3d.picking.PickingSupport;
import org.jzy3d.plot3d.builder.Builder;
import org.jzy3d.plot3d.builder.Mapper;
import org.jzy3d.plot3d.builder.concrete.OrthonormalGrid;
import org.jzy3d.plot3d.primitives.AbstractDrawable;
import org.jzy3d.plot3d.primitives.Point;
import org.jzy3d.plot3d.primitives.Shape;
import org.jzy3d.plot3d.rendering.canvas.Quality;

import pwr.algorithm.EParameters;
import pwr.algorithm.Specimen;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.VarMap;

public class ChartMaker {

	public static void addPointToChart(Specimen specimen, Chart chart, EParameters x1, EParameters x2) {
		Point point = new Point(new Coord3d(specimen.getChromosome().get(x1.ordinal()),
						  			        specimen.getChromosome().get(x2.ordinal()),
						  			        specimen.getScore()),
						  					org.jzy3d.colors.Color.RED,
						  					7.5f);
		if(chart.getScene().getGraph().getBounds().contains(point.getBounds()))
			chart.getScene().getGraph().add(point);
	}
	
	public static void addBestPointToChart(Specimen specimen, Chart chart, EParameters x1, EParameters x2) {
		Point point = new Point(new Coord3d(specimen.getChromosome().get(x1.ordinal()),
						  			        specimen.getChromosome().get(x2.ordinal()),
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
//		final MousePickingController<Object, Object> controller = new MousePickingController<>(chart);
//		controller.setPickingSupport(new PickingSupport());
//		chart.addController(controller);
		
		Shape surface = prepareSurface(equation, variables, rangeXFrom, rangeXTo, rangeYFrom, rangeYTo, stepLength);
//		surface.setLegend(new ColorbarLegend(surface, chart.getView().getAxe().getLayout().getZTickProvider(),
//													  chart.getView().getAxe().getLayout().getZTickRenderer()));
		chart.getScene().getGraph().add(surface);
		
//		chart.getCanvas().addMouseListener(new MouseListener() {
//			
//			@Override
//			public void mouseReleased(MouseEvent arg0) {}
//			
//			@Override
//			public void mousePressed(MouseEvent arg0) {}
//			
//			@Override
//			public void mouseExited(MouseEvent arg0) {}
//			
//			@Override
//			public void mouseEntered(MouseEvent arg0) {}
//			
//			@Override
//			public void mouseClicked(MouseEvent arg0) {
//				if(chart.getCanvas().getView().getCamera().getRectangle().contains(arg0.getPoint())){
//					chart.getCanvas().getView().updateBounds();
//					Coord3d selectedPoint = chart.getCanvas().getView().projectMouse(arg0.getX(), arg0.getY());
//					
//					for(AbstractDrawable drawable : chart.getScene().getGraph().getAll()){
//						if(drawable instanceof Point)
//							if(drawable.getBounds().getCenter().getXY().distance(selectedPoint.getXY())<5)
//								System.out.println("#########XY: " + drawable.getBarycentre().getXY());
//							else
//								System.out.println("No point, clicked: " + selectedPoint.toString());
//					}
//					
////					System.out.println("" + chart.getScene().getGraph().getAll().get(0).getBounds().getCenter().getXY().x);
////					System.out.println("x max: " + chart.getCanvas().getView().getCamera().getRectangle().getMaxX());
////					System.out.println("moved to: " + arg0.getX() + "\t" + arg0.getY());
//				}
//			}
//		});
		
		return chart;
	}

}
