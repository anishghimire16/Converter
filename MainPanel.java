import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * The main graphical panel used to display conversion components.
 * 
 * This is the starting point for the assignment.
 * 
 * The variable names have been deliberately made vague and generic, and most comments have been removed.
 * 
 * You may want to start by improving the variable names and commenting what the existing code does.
 * 
 * @author mdixon
 */
@SuppressWarnings("serial")
public class MainPanel extends JPanel {

	private final static String[] list = { "inches/cm", "Degrees/Radians", "Acres/Hectares", "Miles/Kilometres", "Yards/Metres", "Celsius/Fahrenheit" };
	private JTextField textField;
	private JLabel label;
	private JLabel label2;
	private JComboBox<String> combo;
	private int count=0;


	JMenuBar setupMenu() {

		JMenuBar menuBar = new JMenuBar();

		JMenu m1 = new JMenu("File");
		m1.setMnemonic(KeyEvent.VK_F);
		JMenu m2 = new JMenu("Help");
		m2.setMnemonic(KeyEvent.VK_H);
		JMenu about = new JMenu("About");
		about.setMnemonic(KeyEvent.VK_A);

		menuBar.add(m1);
		menuBar.add(m2);
		menuBar.add(about);
		about.addActionListener(e->{
			JOptionPane.showMessageDialog(new JFrame(), "Author : @AnishGhimire \n Welcome Boys");
		}
		);
		

		JMenuItem exit = new JMenuItem("Exit");
		m1.add(exit);
		exit.addActionListener(e->
		System.exit(0)
		);
		
		

		return menuBar;
	}


	MainPanel() {

		ActionListener listener = new ConvertListener();

		combo = new JComboBox<String>(list);
		combo.addActionListener(listener); //convert values when option changed

		JLabel inputLabel = new JLabel("Enter value:");

		JButton convertButton = new JButton("Convert");
		convertButton.addActionListener(listener); // convert values when pressed
		
		ActionListener clearlistener = new Convertclear();
		JButton clear = new JButton("Clear");
		clear.addActionListener(clearlistener); //clear values when pressed

		label = new JLabel("---");
		textField = new JTextField(5);
		textField.addActionListener(listener);
		
		label2 = new JLabel();
		
		add(combo);
		add(inputLabel);
		add(textField);
		add(convertButton);
		add(label);
		add(clear);
		add(label2);

		setPreferredSize(new Dimension(800, 80));
		setBackground(Color.LIGHT_GRAY);
	}

	private class ConvertListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {

			String text = textField.getText().trim();

			if (text.isEmpty() == false) {
				
				double value = Double.parseDouble(text);

				// the factor applied during the conversion
				double factor = 0;

				// the offset applied during the conversion.
				double offset = 0;

				// Setup the correct factor/offset values depending on required conversion
				switch (combo.getSelectedIndex()) {

				case 0: // inches/cm
					factor = 2.54;
					break;
				case 1: // Degrees/Radians
					factor = 0.0174;
					break;
				case 2: // Acres/Hectares
					factor = 0.4047;
					break;
				case 3: // Miles/Kilometres
					factor = 1.609;
					break;
				case 4: // Yards/Metres
					factor = 0.9144;
					break;
				case 5: // Celsius/Fahrenheit
					factor = 1.8;
					offset = 32;
					break;
				}

				double result = factor * value + offset;
				count++;

				label.setText(Double.toString(result));
				label2.setText("Number of conversions done= "+count);
			}
			
		}
	}
	
	private class Convertclear implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			textField.setText("");
			count=0;
			label2.setText("Number of conversions done= "+count);
			label.setText("");
		}
	}

}
