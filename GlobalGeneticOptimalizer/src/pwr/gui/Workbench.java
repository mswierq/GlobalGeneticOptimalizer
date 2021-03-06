package pwr.gui;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.GridLayout;
import java.awt.Panel;
import java.awt.ScrollPane;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.AbstractButton;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import net.miginfocom.swing.MigLayout;

import org.jzy3d.chart.Chart;
import org.jzy3d.chart.ChartLauncher;
import org.jzy3d.global.Settings;
import org.jzy3d.plot3d.primitives.Point;

import pwr.algorithm.ECross;
import pwr.algorithm.EMutation;
import pwr.algorithm.EParameters;
import pwr.algorithm.GeneticAlgorithm;
import pwr.algorithm.HessianCounter;
import pwr.algorithm.HessianResult;
import pwr.algorithm.Range;
import pwr.algorithm.Specimen;
import pwr.chartCreator.ChartMaker;

import com.graphbuilder.math.Expression;
import com.graphbuilder.math.ExpressionTree;
import com.graphbuilder.math.VarMap;

public class Workbench {

	private ECross selectedCrossAlgorithm;
	private EMutation selectedMutationAlgorithm;
	
	private JFrame frmGlobalgenericoptimalizer;
	private JTextField equationTextField;
	private JTextField rangeX1FromText;
	private JTextField rangeX1ToText;
	private JTextField stepsLengthTextField;
	private JTextField rangeX2FromText;
	private JTextField rangeX2ToText;
	private JTextField rangeX3FromText;
	private JTextField rangeX3ToText;
	private JTextField rangeX4FromText;
	private JTextField rangeX4ToText;
	private JTextField rangeX5FromText;
	private JTextField rangeX5ToText;
	private JTextField iterationsText;
	private JTextField crossProbabilityText;
	private JTextField mutationProbabilityText;
	private JLabel lblChartSettings;
	private ButtonGroup crossGroup;
	private ButtonGroup mutationGroup;
	private JCheckBox checkBoxX1;
	private AbstractButton checkBoxX2;
	private JCheckBox checkBoxX3;
	private JCheckBox checkBoxX4;
	private JCheckBox checkBoxX5;
	private JCheckBox checkBoxShowChart;
	private JLabel lblResultX1;
	private JLabel lblResultScore;
	private JLabel lblResultX2;
	private JLabel lblResultX3;
	private JLabel lblResultX4;
	private JLabel lblResultX5;
	private JCheckBox stopCritrionCheckBox;
	
	private static GeneticAlgorithm geneticAlgorithm = null;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Workbench window = new Workbench();
					window.frmGlobalgenericoptimalizer.setVisible(true);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public Workbench() {
		initialize();
		
		this.selectedCrossAlgorithm = ECross.arithmetic;
		this.selectedMutationAlgorithm = EMutation.equal;
	}

	private void initialize() {
		Settings.getInstance().setHardwareAccelerated(true);
		
		frmGlobalgenericoptimalizer = new JFrame();
		frmGlobalgenericoptimalizer.setResizable(true);
		frmGlobalgenericoptimalizer.setTitle("GlobalGenericOptimalizer");
		frmGlobalgenericoptimalizer.setBounds(100, 100, 651, 704);
		frmGlobalgenericoptimalizer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frmGlobalgenericoptimalizer.getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		
		Panel panel = new Panel();
		ScrollPane scrollPanel = new ScrollPane();
		scrollPanel.add(panel);
		frmGlobalgenericoptimalizer.getContentPane().add(scrollPanel);
		panel.setLayout(new MigLayout("", "[81.00px,grow][77.00px,grow,fill][46.00px][77.00px,grow,center][35px,fill]", "[][][][23px][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][][]"));
		
		
		//=======================	USTAWIENIA FUNKCJI	=================================//
		JSeparator separator_1 = new JSeparator();
		panel.add(separator_1, "cell 0 0 5 1,grow");
		
		JLabel lblFunctionSettings = new JLabel("USTAWIENIA FUNKCJI");
		panel.add(lblFunctionSettings, "cell 0 1 5 1,alignx center");
		
		JSeparator separator_6 = new JSeparator();
		panel.add(separator_6, "cell 0 2 5 1,grow");
		
		JLabel formulaLabel = new JLabel("Wprowad� formu��: ");
		formulaLabel.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(formulaLabel, "cell 0 3,alignx left,aligny center");
		
		equationTextField = new JTextField();
		equationTextField.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(equationTextField, "cell 1 3 3 1,growx,aligny center");
		equationTextField.setColumns(15);
		
		JButton button = new JButton("+");
		button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				new ParserFunctionPicker(equationTextField).main(equationTextField);
			}
		});
		panel.add(button, "cell 4 3");
		
		checkBoxX1 = new JCheckBox("Zakres x1: (");
		checkBoxX1.setSelected(true);
		panel.add(checkBoxX1, "cell 0 4,alignx trailing");
		
			rangeX1FromText = new JTextField();
			panel.add(rangeX1FromText, "cell 1 4,growx");
			rangeX1FromText.setColumns(10);
			
			JLabel lblCommaX = new JLabel(",");
			panel.add(lblCommaX, "cell 2 4,alignx center");
			
			rangeX1ToText = new JTextField();
			panel.add(rangeX1ToText, "cell 3 4,growx");
			rangeX1ToText.setColumns(10);
			
			JLabel lblCloseColonX1 = new JLabel(")");
			panel.add(lblCloseColonX1, "cell 4 4");
		
		checkBoxX2 = new JCheckBox("Zakres x2: (");
		checkBoxX2.setSelected(true);
		panel.add(checkBoxX2, "cell 0 5,alignx trailing");
		
			rangeX2FromText = new JTextField();
			rangeX2FromText.setText("-25");
			rangeX2FromText.setColumns(10);
			panel.add(rangeX2FromText, "cell 1 5,growx");
			
			JLabel lblCommaX1 = new JLabel(",");
			panel.add(lblCommaX1, "cell 2 5,alignx center");
			
			rangeX2ToText = new JTextField();
			rangeX2ToText.setText("25");
			rangeX2ToText.setColumns(10);
			panel.add(rangeX2ToText, "cell 3 5,growx");
			
			JLabel lblCloseColonX2 = new JLabel(")");
			panel.add(lblCloseColonX2, "cell 4 5");
		
		checkBoxX3 = new JCheckBox("Zakres x3: (");
		panel.add(checkBoxX3, "cell 0 6,alignx trailing");
		
			rangeX3FromText = new JTextField();
			rangeX3FromText.setEnabled(false);
			panel.add(rangeX3FromText, "cell 1 6,growx");
			rangeX3FromText.setColumns(10);
			
			JLabel lblCommaX3 = new JLabel(",");
			panel.add(lblCommaX3, "cell 2 6,alignx center");
			
			rangeX3ToText = new JTextField();
			rangeX3ToText.setEnabled(false);
			panel.add(rangeX3ToText, "cell 3 6,growx");
			rangeX3ToText.setColumns(10);
			
			JLabel lblCloseColonX3 = new JLabel(")");
			panel.add(lblCloseColonX3, "cell 4 6");
		
		checkBoxX4 = new JCheckBox("Zakres x4: (");
		checkBoxX4.setEnabled(false);
		panel.add(checkBoxX4, "cell 0 7,alignx right");
		
			rangeX4FromText = new JTextField();
			rangeX4FromText.setEnabled(false);
			panel.add(rangeX4FromText, "cell 1 7,growx");
			rangeX4FromText.setColumns(10);
			
			JLabel lblCommaX4 = new JLabel(",");
			panel.add(lblCommaX4, "cell 2 7,alignx center");
			
			rangeX4ToText = new JTextField();
			rangeX4ToText.setEnabled(false);
			panel.add(rangeX4ToText, "cell 3 7,growx");
			rangeX4ToText.setColumns(10);
			
			JLabel lblCloseColonX4 = new JLabel(")");
			panel.add(lblCloseColonX4, "cell 4 7");
		
		checkBoxX5 = new JCheckBox("Zakres x5: (");
		checkBoxX5.setEnabled(false);
		panel.add(checkBoxX5, "cell 0 8,alignx right");
		
			rangeX5FromText = new JTextField();
			rangeX5FromText.setEnabled(false);
			panel.add(rangeX5FromText, "cell 1 8,growx");
			rangeX5FromText.setColumns(10);
			
			JLabel lblCommaX5 = new JLabel(",");
			panel.add(lblCommaX5, "cell 2 8,alignx center");
			
			rangeX5ToText = new JTextField();
			rangeX5ToText.setEnabled(false);
			panel.add(rangeX5ToText, "cell 3 8,growx");
			rangeX5ToText.setColumns(10);
			
			JLabel lblCloseColonX5 = new JLabel(")");
			panel.add(lblCloseColonX5, "cell 4 8");
		
		JSeparator separator_2 = new JSeparator();
		panel.add(separator_2, "cell 0 9 5 1,grow");
		
		//=======================	USTAWIENIA ALGORYTMU	=================================//
		JLabel lblAlgorithmSettings = new JLabel("USTAWIENIA ALGORYTMU");
		panel.add(lblAlgorithmSettings, "cell 0 10 5 1,alignx center");
		
		JSeparator separator = new JSeparator();
		panel.add(separator, "cell 0 11 5 1,grow");
		
		JLabel lblIterations = new JLabel("Liczba iteracji: ");
		panel.add(lblIterations, "flowx,cell 0 12 2 1");
		
		iterationsText = new JTextField();
		panel.add(iterationsText, "cell 2 12");
		iterationsText.setColumns(10);
		
		JLabel lblCrossProbability = new JLabel("Prawdopodobie\u0144\u015Btwo krzy\u017Cowania:");
		panel.add(lblCrossProbability, "flowx,cell 0 13 2 1");
		
		crossProbabilityText = new JTextField();
		panel.add(crossProbabilityText, "cell 2 13");
		crossProbabilityText.setColumns(10);
		
		JLabel lblMutationProbability = new JLabel("Prawdopodobie\u0144stwo mutacji: ");
		panel.add(lblMutationProbability, "flowx,cell 0 14 2 1");
		
		mutationProbabilityText = new JTextField();
		panel.add(mutationProbabilityText, "cell 2 14");
		mutationProbabilityText.setColumns(10);
		
		JSeparator separator_7 = new JSeparator();
		panel.add(separator_7, "cell 0 15 5 1,grow");
		
		JLabel lblCrossRadioGroup = new JLabel("Krzy\u017Cowanie:");
		panel.add(lblCrossRadioGroup, "cell 0 16 2 1,alignx center");
		
		JSeparator separator_4 = new JSeparator();
		separator_4.setOrientation(SwingConstants.VERTICAL);
		panel.add(separator_4, "cell 2 16 1 4,alignx center,growy");
		
		JLabel lblMutacja = new JLabel("Mutacja:");
		panel.add(lblMutacja, "cell 3 16 2 1,alignx center");
		
		crossGroup = new ButtonGroup();
		mutationGroup = new ButtonGroup();
		
		JRadioButton rdbtnSimple = new JRadioButton("Proste");
		rdbtnSimple.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				selectedCrossAlgorithm = ECross.simple;
			}
		});
		panel.add(rdbtnSimple, "cell 0 17 2 1,alignx left");
		
		JRadioButton rdbtnEqual = new JRadioButton("R\u00F3wnomierna");
		rdbtnEqual.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				selectedMutationAlgorithm = EMutation.equal;
			}
		});
		rdbtnEqual.setSelected(true);
		panel.add(rdbtnEqual, "cell 3 17 2 1,alignx left");
		
		JRadioButton rdbtnArithmetic = new JRadioButton("Arytmetyczne");
		rdbtnArithmetic.setSelected(true);
		rdbtnArithmetic.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				selectedCrossAlgorithm = ECross.arithmetic;
			}
		});
		panel.add(rdbtnArithmetic, "cell 0 18 2 1,alignx left");
		
		JRadioButton rdbtnUnequal = new JRadioButton("Nier\u00F3wnomierna");
		rdbtnUnequal.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				selectedMutationAlgorithm = EMutation.unequal;
			}
		});
		panel.add(rdbtnUnequal, "cell 3 18 2 1,alignx left");
		
		JRadioButton rdbtnGradient = new JRadioButton("Gradientowa");
		rdbtnGradient.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				selectedMutationAlgorithm = EMutation.gradient;
			}
		});
		panel.add(rdbtnGradient, "cell 3 19 2 1,alignx left");
		
		crossGroup.add(rdbtnSimple);
		crossGroup.add(rdbtnArithmetic);
		
		mutationGroup.add(rdbtnEqual);
		mutationGroup.add(rdbtnUnequal);
		mutationGroup.add(rdbtnGradient);
		
		JSeparator separator_10 = new JSeparator();
		panel.add(separator_10, "cell 0 20 5 1,growx");
		
		stopCritrionCheckBox = new JCheckBox("W��cz kryterium stopu");
		stopCritrionCheckBox.setSelected(true);
		panel.add(stopCritrionCheckBox, "cell 0 21");
		
		//=======================	USTAWIENIA WYKRESU	=================================//
		JSeparator separator_3 = new JSeparator();
		panel.add(separator_3, "cell 0 23 5 1,grow");
		
		lblChartSettings = new JLabel("USTAWIENIA WYKRESU");
		panel.add(lblChartSettings, "cell 0 24 5 1,alignx center");
		
		checkBoxShowChart = new JCheckBox("Poka\u017C wykres");
		checkBoxShowChart.setSelected(true);
		panel.add(checkBoxShowChart, "cell 0 25,alignx center");
		
		JLabel lblStep = new JLabel("Krok:");
		panel.add(lblStep, "cell 2 25,alignx trailing");
		
		stepsLengthTextField = new JTextField();
		panel.add(stepsLengthTextField, "cell 3 25,growx");
		stepsLengthTextField.setColumns(10);
		
		JSeparator separator_5 = new JSeparator();
		panel.add(separator_5, "cell 0 27 5 1,grow");
		
		JButton btnRunner = new JButton("Start");
		btnRunner.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Expression equation = ExpressionTree.parse(equationTextField.getText());
				
				runGeneticAlgorithm(equation);
				
				if(checkBoxShowChart.isSelected()){
					printChart(equation);
				}
			}
		});
		
		JButton btnRedrawChart = new JButton("Przerysuj wykres");
		btnRedrawChart.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Expression equation = ExpressionTree.parse(equationTextField.getText());
				printChart(equation);
			}
		});
		
		btnRedrawChart.setSize(20, 40);
		panel.add(btnRedrawChart, "flowx,cell 0 28,alignx center");
		
		JButton btnHesjan = new JButton("Hesjan");
		btnHesjan.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				if(geneticAlgorithm == null){
					Expression equation = ExpressionTree.parse(equationTextField.getText());
					Map<EParameters,Range> limits = setFunctionLimits();
					HessianResult result = HessianCounter.countHessian(limits, equation);
					new HessianDisplayer(result).main(result);	
				} else {
					Expression equation = ExpressionTree.parse(equationTextField.getText());
					Map<EParameters,Range> limits = setFunctionLimits();
					HessianResult result = HessianCounter.countHessian(limits, equation, geneticAlgorithm.getBestMatch());
					new HessianDisplayer(result).main(result);	
				}
			}
		});
		panel.add(btnHesjan, "cell 3 28,alignx center");
		
		btnRunner.setSize(20, 20);
		panel.add(btnRunner, "cell 4 28,alignx center,aligny top");
		
		JSeparator separator_8 = new JSeparator();
		panel.add(separator_8, "cell 0 32 5 1,grow");
		
		JLabel lblBestMatch = new JLabel("NAJLEPSZE DOPASOWANIE:");
		panel.add(lblBestMatch, "cell 0 33 5 1,alignx center");
		
		JSeparator separator_9 = new JSeparator();
		panel.add(separator_9, "cell 0 34 5 1,grow");
		
		JLabel lblBestX1 = new JLabel("X1: ");
		panel.add(lblBestX1, "cell 0 36,alignx right");
		
		lblResultX1 = new JLabel("");
		panel.add(lblResultX1, "cell 1 36,alignx left");
		
		JLabel lblNewLabel = new JLabel("f([x]): ");
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel.add(lblNewLabel, "cell 2 36,alignx right");
		
		lblResultScore = new JLabel("");
		panel.add(lblResultScore, "cell 3 36,alignx left");
		
		JLabel lblBestX2 = new JLabel("X2: ");
		panel.add(lblBestX2, "cell 0 37,alignx right");
		
		lblResultX2 = new JLabel("");
		panel.add(lblResultX2, "cell 1 37,alignx left");
		
		JLabel lblBestX3 = new JLabel("X3: ");
		panel.add(lblBestX3, "cell 0 38,alignx right");
		
		lblResultX3 = new JLabel("");
		panel.add(lblResultX3, "cell 1 38,alignx left");
		
		JLabel lblBestX4 = new JLabel("X4: ");
		panel.add(lblBestX4, "cell 0 39,alignx right");
		
		lblResultX4 = new JLabel("");
		panel.add(lblResultX4, "cell 1 39,alignx left");
		
		JLabel lblBestX5 = new JLabel("X5: ");
		panel.add(lblBestX5, "cell 0 40,alignx right");
		
		lblResultX5 = new JLabel("");
		panel.add(lblResultX5, "cell 1 40,alignx left");
		
		addWidgetsListeners();
		initWidgetsValues();
	}

	private void addWidgetsListeners() {
		checkBoxX1.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				rangeX1FromText.setEnabled(checkBoxX1.isSelected());
				rangeX1ToText.setEnabled(checkBoxX1.isSelected());
				if(checkBoxX1.isSelected()) {
					checkBoxX2.setEnabled(true);
				}
				else {
					checkBoxX2.setEnabled(false);
					checkBoxX2.setSelected(false);
				}
			}
		});
		
		checkBoxX2.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				rangeX2FromText.setEnabled(checkBoxX2.isSelected());
				rangeX2ToText.setEnabled(checkBoxX2.isSelected());
				if(checkBoxX2.isSelected()) {
					checkBoxX3.setEnabled(true);
				}
				else {
					checkBoxX3.setEnabled(false);
					checkBoxX3.setSelected(false);
				}
			}
		});
		
		checkBoxX3.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				rangeX3FromText.setEnabled(checkBoxX3.isSelected());
				rangeX3ToText.setEnabled(checkBoxX3.isSelected());
				if(checkBoxX3.isSelected()) {
					checkBoxX4.setEnabled(true);
				}
				else {
					checkBoxX4.setEnabled(false);
					checkBoxX4.setSelected(false);
				}
			}
		});
		
		checkBoxX4.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				rangeX4FromText.setEnabled(checkBoxX4.isSelected());
				rangeX4ToText.setEnabled(checkBoxX4.isSelected());
				if(checkBoxX4.isSelected()) {
					checkBoxX5.setEnabled(true);
				}
				else {
					checkBoxX5.setEnabled(false);
					checkBoxX5.setSelected(false);
				}
			}
		});
		
		checkBoxX5.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent arg0) {
				rangeX5FromText.setEnabled(checkBoxX5.isSelected());
				rangeX5ToText.setEnabled(checkBoxX5.isSelected());
			}
		});
		
		checkBoxShowChart.addItemListener(new ItemListener() {
			public void itemStateChanged(ItemEvent e) {
				stepsLengthTextField.setEnabled(checkBoxShowChart.isSelected());
			}
		});
	}
	
	private void initWidgetsValues() {
//		equationTextField.setText("(4-2.1*x1^2+x1^(4/3))*x1^2+x1*x2+(-4+4*x2^2)*x2^2");  //Six-hump camel back function
																						 //Funkcja przy ktorej pojawia sie NaN
																						 //global minimum: f(x1,x2)=-1.0316; (x1,x2)=(-0.0898,0.7126), (0.0898,-0.7126).
		equationTextField.setText("(1+(x1+x2+1)^2*(19-14*x1+3*x1^2-14*x2+6*x1*x2+3*x2^2))*(30+(2*x1-3*x2)^2*(18-32*x1+12*x1^2+48*x2-36*x1*x2+27*x2^2))"); //Goldstein-Price
//		equationTextField.setText("1*(x2-(5.1/(4*pi^2))*x1^2+(5/pi)*x1-6)^2+10*(1-(1/(8*pi)))*cos(x1)+10"); //Branins function
																						//global minimum: f(x1,x2)=0.397887; (x1,x2)=(-pi,12.275), (pi,2.275), (9.42478,2.475)
//WIELE ZMIENNYCH
//		equationTextField.setText("10*5+(x1^2-10*cos(2*pi*x1)+x2^2-10*cos(2*pi*x2)+x3^2-10*cos(2*pi*x3)+x4^2-10*cos(2*pi*x4)+x5^2-10*cos(2*pi*x5))"); //Rastrigin - minimum w zerze
//		equationTextField.setText("-20*e^(-0.2*((1/5*(x1^2+x2^2+x3^2+x4^2+x5^2)))^(1/2))-e^(1/5*(cos(2*pi*x1)+cos(2*pi*x2)+cos(2*pi*x3)+cos(2*pi*x4)+cos(2*pi*x5)))+20+e^(1)"); //Ackley - minimum w zerze
//		equationTextField.setText("-1*(sin(x1)*(sin(1*x1^2/pi))^(2*10)+sin(x2)*(sin(2*x2^2/pi))^(2*10)+sin(x3)*(sin(3*x3^2/pi))^(2*10)+sin(x4)*(sin(4*x4^2/pi))^(2*10)+sin(x5)*(sin(5*x5^2/pi))^(2*10))"); //Michalewicz gdy n min w -4.687		
		rangeX1FromText.setText("-3");
		rangeX1ToText.setText("3");
		rangeX2FromText.setText("-3");
		rangeX2ToText.setText("3");
		
		iterationsText.setText("2500");
		crossProbabilityText.setText("0.6");
		mutationProbabilityText.setText("0.2");
		
		stepsLengthTextField.setText("0.25");
	}
	
	private void runGeneticAlgorithm(Expression equation) {
		Map<EParameters,Range> limits = setFunctionLimits();
		geneticAlgorithm  = new GeneticAlgorithm(limits,
										   		 getIntegerTextValue(iterationsText),
												 getDoubleTextValue(crossProbabilityText),
												 getDoubleTextValue(mutationProbabilityText),
											     selectedCrossAlgorithm,
												 selectedMutationAlgorithm,
												 equation);
			System.out.println(geneticAlgorithm);
			System.out.println();
			
			lblResultScore.setText("Obliczanie");
			
			if(stopCritrionCheckBox.isSelected()) {
				geneticAlgorithm.enableStopCriterion(true);
			} else {
				geneticAlgorithm.enableStopCriterion(false);
			}
			
			geneticAlgorithm.execute();
			
			printResults(geneticAlgorithm.getBestMatch());
			System.out.println(geneticAlgorithm);	
	}

	private void printResults(Specimen bestMatch) {
		if(checkBoxX1.isSelected())
			lblResultX1.setText(bestMatch.getChromosome().get(EParameters.X1.ordinal()).toString());
		if(checkBoxX2.isSelected())
			lblResultX2.setText(bestMatch.getChromosome().get(EParameters.X2.ordinal()).toString());
		if(checkBoxX3.isSelected())
			lblResultX3.setText(bestMatch.getChromosome().get(EParameters.X3.ordinal()).toString());
		if(checkBoxX4.isSelected())
			lblResultX4.setText(bestMatch.getChromosome().get(EParameters.X4.ordinal()).toString());
		if(checkBoxX5.isSelected())
			lblResultX5.setText(bestMatch.getChromosome().get(EParameters.X5.ordinal()).toString());
		Color lblColor = bestMatch.getScore().toString().contains("E-")==true ? Color.MAGENTA : Color.BLACK;
		lblResultScore.setForeground(lblColor);
		lblResultScore.setText(bestMatch.getScore().toString());
		this.frmGlobalgenericoptimalizer.repaint();
	}

	private void printChart(Expression equation) {
		double stepLength = Double.parseDouble(stepsLengthTextField.getText());
		
		HashMap<EParameters,Range> ranges = getRanges();
		
		VarMap variables = new VarMap(false);
		
		ArrayList<EParameters> vars = prepareVarMap(variables);
		
		EParameters varX = vars.get(0);
		EParameters varY = vars.get(1);
		
		//Przygotowanie wykresu funkcji
		Chart chart = ChartMaker.prepareChart(equation, variables, 
											   ranges.get(varX).getMin(),
											   ranges.get(varX).getMax(),
											   ranges.get(varY).getMin(),
											   ranges.get(varY).getMax(),
											   stepLength);
		
		//Przygotowanie najlepszego punktu
		Point point = null;
		if(geneticAlgorithm != null) {
			for(int i=0; i<geneticAlgorithm.getResults().getMatchTrace().size(); i++){
				ChartMaker.addPointToChart(geneticAlgorithm.getResults().getMatchTrace().get(i), chart, varX, varY);
			}
			
			ChartMaker.addBestPointToChart(geneticAlgorithm.getBestMatch(), chart, varX, varY);
		}

		//Rysowanie wykresu
		ChartLauncher.openChart(chart,equation.toString());
	}
	
	private ArrayList<EParameters> prepareVarMap(VarMap variables) {
		int counter = 0;
		Double zero = new Double(0.0);
		
		EParameters varX = EParameters.X1;
		EParameters varY = EParameters.X2;
		
		HashMap<EParameters, Range> ranges = getRanges();
		
		variables.setValue("x1", 0.0);
		variables.setValue("x2", 0.0);
		variables.setValue("x3", 0.0);
		variables.setValue("x4", 0.0);
		variables.setValue("x5", 0.0);
		variables.setValue("pi", Math.PI);
		variables.setValue("e", Math.E);
		
		if(checkBoxX1.isSelected()) {
			variables.setValue("x1", getDoubleTextValue(rangeX1FromText));
			if(!ranges.get(EParameters.X1).getRange().equals(zero)) {
				varX = EParameters.X1;
				counter++;
			}
		}
		if(checkBoxX2.isSelected()) {
			variables.setValue("x2", getDoubleTextValue(rangeX2FromText));
			if(counter == 0 && !ranges.get(EParameters.X2).getRange().equals(zero)) {
				varX = EParameters.X2;
				counter++;
			} else if(counter == 1 && !ranges.get(EParameters.X2).getRange().equals(zero)) {
				varY = EParameters.X2;
				counter++;
			}
		}
		if(checkBoxX3.isSelected()) {
			variables.setValue("x3", getDoubleTextValue(rangeX3FromText));
			if(counter == 0 && !ranges.get(EParameters.X3).getRange().equals(zero)) {
				varX = EParameters.X3;
				counter++;
			} else if(counter == 1 && !ranges.get(EParameters.X3).getRange().equals(zero)) {
				varY = EParameters.X3;
				counter++;
			} else if(counter > 1) {
				variables.setValue("x3", 0.0);
			}
		}
		if(checkBoxX4.isSelected()) {
			variables.setValue("x4", getDoubleTextValue(rangeX4FromText));
			if(counter == 0 && !ranges.get(EParameters.X4).getRange().equals(zero)) {
				varX = EParameters.X4;
				counter++;
			} else if(counter == 1 && !ranges.get(EParameters.X4).getRange().equals(zero)) {
				varY = EParameters.X4;
				counter++;
			} else if(counter > 1) {
				variables.setValue("x4", 0.0);
			}
		}
		if(checkBoxX5.isSelected()) {
			variables.setValue("x5", getDoubleTextValue(rangeX5FromText));
			if(counter == 0 && !ranges.get(EParameters.X5).getRange().equals(zero)) {
				varX = EParameters.X5;
				counter++;
			} else if(counter == 1 && !ranges.get(EParameters.X5).getRange().equals(zero)) {
				varY = EParameters.X5;
				counter++;
			} else if(counter > 1) {
				variables.setValue("x5", 0.0);
			}
		}
		
		ArrayList<EParameters> vars = new ArrayList<EParameters>(2);
		vars.add(varX);
		vars.add(varY);
		
		return vars;
	}
	
	private Double getDoubleTextValue(JTextField text){
		return Double.parseDouble(text.getText());
	}
	
	private Integer getIntegerTextValue(JTextField text){
		return Integer.parseInt(text.getText());
	}
	
	private Map<EParameters,Range> setFunctionLimits(){
		Map<EParameters,Range> limits = new HashMap<EParameters,Range>();
		
		if(checkBoxX1.isSelected())
			limits.put(EParameters.X1, new Range(getDoubleTextValue(rangeX1FromText),
												 getDoubleTextValue(rangeX1ToText)));
		if(checkBoxX2.isSelected())
			limits.put(EParameters.X2, new Range(getDoubleTextValue(rangeX2FromText),
												 getDoubleTextValue(rangeX2ToText)));
		if(checkBoxX3.isSelected())
			limits.put(EParameters.X3, new Range(getDoubleTextValue(rangeX3FromText),
												 getDoubleTextValue(rangeX3ToText)));
		if(checkBoxX4.isSelected())
			limits.put(EParameters.X4, new Range(getDoubleTextValue(rangeX4FromText),
												 getDoubleTextValue(rangeX4ToText)));
		if(checkBoxX5.isSelected())
			limits.put(EParameters.X5, new Range(getDoubleTextValue(rangeX5FromText),
												 getDoubleTextValue(rangeX5ToText)));
		
		return limits;
	}
	
	private HashMap<EParameters, Range> getRanges() {
		HashMap<EParameters, Range> ranges = new HashMap<EParameters, Range>();
		
		if(checkBoxX1.isSelected()) {
			ranges.put(EParameters.X1,new Range(getDoubleTextValue(rangeX1FromText),
												getDoubleTextValue(rangeX1ToText)));
		}
		if(checkBoxX2.isSelected()) {
			ranges.put(EParameters.X2,new Range(getDoubleTextValue(rangeX2FromText),
												getDoubleTextValue(rangeX2ToText)));
		}
		if(checkBoxX3.isSelected()) {
			ranges.put(EParameters.X3,new Range(getDoubleTextValue(rangeX3FromText),
												getDoubleTextValue(rangeX3ToText)));
		}
		if(checkBoxX4.isSelected()) {
			ranges.put(EParameters.X4,new Range(getDoubleTextValue(rangeX4FromText),
												getDoubleTextValue(rangeX4ToText)));
		}
		if(checkBoxX5.isSelected()) {
			ranges.put(EParameters.X5,new Range(getDoubleTextValue(rangeX5FromText),
												getDoubleTextValue(rangeX5ToText)));
		}
		
		return ranges;
	}
}
