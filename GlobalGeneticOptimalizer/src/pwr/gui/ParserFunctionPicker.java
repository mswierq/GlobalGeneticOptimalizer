package pwr.gui;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.border.EmptyBorder;

import pwr.parser.FunctionMapBase;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class ParserFunctionPicker extends JFrame {
	private static final long serialVersionUID = -1286406379669375604L;
	private JPanel contentPane;

	public static void main(final JTextField equationTextField) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ParserFunctionPicker frame = new ParserFunctionPicker(equationTextField);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ParserFunctionPicker(final JTextField equationTextField) {
		getContentPane().setLayout(new GridLayout(1, 0, 0, 0));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 295, 446);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.LABEL_COMPONENT_GAP_COLSPEC,
				ColumnSpec.decode("264px"),},
			new RowSpec[] {
				FormFactory.LINE_GAP_ROWSPEC,
				RowSpec.decode("fill:350px"),
				RowSpec.decode("53px"),}));
		
		JScrollPane scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "2, 2, fill, fill");
		
		final JList<String> list = new JList<String>();
		scrollPane.setViewportView(list);
		list.setVisibleRowCount(10);
		list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		list.setListData(new FunctionMapBase().getFunctionNames());
		
		JButton btnNewButton = new JButton("Wybierz");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
//				equationTextField.getCaret().getDot();
				StringBuffer resultText = new StringBuffer(equationTextField.getText());
				resultText.insert(equationTextField.getSelectionEnd(), (String) list.getSelectedValue());
				equationTextField.setText(resultText.toString());
//				currentText.substring(0, equationTextField.getSelectionEnd());
			}
		});
		contentPane.add(btnNewButton, "2, 3");
	}

}
