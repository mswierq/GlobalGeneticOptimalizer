package pwr.gui;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;

import pwr.algorithm.HessianResult;
import javax.swing.JScrollPane;

public class HessianDisplayer extends JFrame {
	private static final long serialVersionUID = -3762036190420409238L;
	private JPanel contentPane;

	public static void main(final HessianResult result) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					HessianDisplayer frame = new HessianDisplayer(result);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public HessianDisplayer(HessianResult result) {
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new GridLayout(2, 1, 15, 15));
		
		JScrollPane scrollPaneHessian = new JScrollPane();
		contentPane.add(scrollPaneHessian);
		
		JPanel panelHessian = new JPanel();
		scrollPaneHessian.setViewportView(panelHessian);
		panelHessian.setLayout(new GridLayout(result.getHessian().length, result.getHessian().length, 15, 15));
		
		JScrollPane scrollPaneEquations = new JScrollPane();
		contentPane.add(scrollPaneEquations);
		
		JPanel panelEquations = new JPanel();
		scrollPaneEquations.setViewportView(panelEquations);
		panelEquations.setLayout(new GridLayout(result.getHessian().length, 1, 15, 15));
		
		int rowIndex = 0;
		for(String[] expressionRow : result.getHessian()){
			JTextArea equationArea = new JTextArea("x" + (rowIndex+1) + ": " + result.getFirstDiffList().get(rowIndex) + " = 0");
				equationArea.setLineWrap(true);
				panelEquations.add(equationArea);
			int columnIndex = 1;
			for(String e : expressionRow){
				JPanel panel = new JPanel();
					panel.setBorder(new TitledBorder(null, "d/(dx" + (rowIndex+1) + "x" + columnIndex + ")", TitledBorder.LEADING, TitledBorder.TOP, null, null));
					panel.setLayout(new GridLayout(1, 1, 15, 15));
					panelHessian.add(panel);
				JTextArea textArea = new JTextArea();
					textArea.setLineWrap(true);
					textArea.setText("" + e);
					panel.add(textArea);
				columnIndex++;
			}
			rowIndex++;
		}
	}

}
