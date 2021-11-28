import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.text.DecimalFormat;

import javax.swing.JButton;
import javax.swing.JCheckBox;
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
	private JLabel countlabel;
	private JComboBox<String> combo;
	private JCheckBox reverse;
	private int count=0;
	private double result=0;
	private String unit1;
	private JLabel unitlabel1;
	private String unit2;
	private JLabel unitlabel2;
	
	
	JMenuBar setupMenu() {

		JMenuBar menuBar = new JMenuBar();

		JMenu file = new JMenu("File");
		file.setMnemonic(KeyEvent.VK_F);
		file.setToolTipText("File");
		JMenu help = new JMenu("Help");
		help.setMnemonic(KeyEvent.VK_H);
		help.setToolTipText("Open Help");
		JMenu about = new JMenu("About");
		about.setMnemonic(KeyEvent.VK_M);
		about.setToolTipText("Open About");

		menuBar.add(file);
		menuBar.add(help);
		
		help.add(about);
		about.addActionListener(e->
		{
			JOptionPane.showMessageDialog(new JFrame(), "Author : @AnishGhimire \n Welcome Boys");
		});
		//JOptionPane.showMessageDialog(new JFrame(), "Author : @AnishGhimire \n Welcome Boys");
		
		
		JMenuItem exit = new JMenuItem("Exit");
		file.add(exit);
		exit.setMnemonic(KeyEvent.VK_E);
		exit.addActionListener(e->
		{
			int stat = JOptionPane.showConfirmDialog(new JFrame(),"Do you really want to exit ?", "Exit Program Confirmation", JOptionPane.YES_NO_OPTION);
			if (stat==JOptionPane.YES_OPTION)
				System.exit(0);
		}
		);
		exit.setToolTipText("Exit to Window");
		
		return menuBar;
	}


	MainPanel() {

		ActionListener listener = new ConvertListener();

		combo = new JComboBox<String>(list);
//		combo.addActionListener(listener); //convert values when option changed
		combo.setToolTipText("Select Conversion");

		JLabel inputLabel = new JLabel("Enter value:");

		JButton convertButton = new JButton("Convert");
		convertButton.addActionListener(listener); // convert values when pressed
		convertButton.setToolTipText("Perform Conversion");
		
		ActionListener clearlistener = new Convertclear();
		JButton clear = new JButton("Clear");
		clear.addActionListener(clearlistener); //clear values when pressed
		clear.setToolTipText("Clear the text field");

		label = new JLabel("---");
		textField = new JTextField(5);
		textField.addActionListener(listener);
		textField.setToolTipText("Enter the value here");
		
		countlabel = new JLabel();
		
		reverse=new JCheckBox("Reverse");
		reverse.setToolTipText("Perform reverse conversion");
		
		unitlabel1=new JLabel();
		unitlabel2=new JLabel();
		
		add(combo);
		add(inputLabel);
		add(textField);
		add(unitlabel1);
		add(convertButton);
		add(label);
		add(unitlabel2);
		add(reverse);
		add(clear);
		add(countlabel);

		setPreferredSize(new Dimension(800, 80));
		setBackground(Color.LIGHT_GRAY);
		
		CheckBoxListener listener2 = new CheckBoxListener();
		reverse.addItemListener(listener2);
		
		
	}
	
	
	
	private class CheckBoxListener implements ItemListener {
		
		public void itemStateChanged(ItemEvent item) 
		{
			if(reverse.isSelected())
			{
				String text = textField.getText().trim();
				try {
			if (text.isEmpty() == false) {
				
				double value = Double.parseDouble(text);

				// the factor applied during the conversion
				double factor = 0;

				// the offset applied during the conversion.
				double offset = 0;

				// Setup the correct factor/offset values depending on required conversion
				switch (combo.getSelectedIndex()) {

				case 0: // inches/cm
					factor = 0.394;
					unit1="cm";
					unit2="inches";
					break;
				case 1: // Degrees/Radians
					factor = 57.296;
					unit1="radian";
					unit2="degree";
					break;
				case 2: // Acres/Hectares
					factor = 2.471;
					unit1="hectares";
					unit2="acres";
					break;
				case 3: // Miles/Kilometres
					factor = 0.621;
					unit1="Kilometres";
					unit2="miles";
					break;
				case 4: // Yards/Metres
					factor = 1.094;
					unit1="Metres";
					unit2="yards";
					break;
				case 5: // Celsius/Fahrenheit
					factor = 0.556;
					offset = -17.78;
					unit1="Fahrenheit";
					unit2="celsius";
					break;
				case 6: //Pounds/Kilogram
					factor = 2.205;
					offset = 0;
					unit1="Kilogram";
					unit2="pounds";
				}

				result=factor*value+offset ;//to find the reverse value
				count++; 
				DecimalFormat df=new DecimalFormat("#.##");
				String temp=df.format(result);
				label.setText(temp);
				
				unitlabel1.setText(unit1);
				
				countlabel.setText("Number of conversions done= "+count);
				unitlabel2.setText(unit2);
				
				
				}
				
			}
			
			catch(NumberFormatException e)
			{
				String error;
				error="Please enter valid numbers only !!!";
				JOptionPane.showMessageDialog(new JFrame(), error, "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			}
			
		}
	}
	
	
	

	private class ConvertListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent event) {
			
			try {
			String text = textField.getText().trim();
			
			if (text.isEmpty() == false){
				
				double value = Double.parseDouble(text);

				// the factor applied during the conversion
				double factor = 0;

				// the offset applied during the conversion.
				double offset = 0;

				// Setup the correct factor/offset values depending on required conversion
				switch (combo.getSelectedIndex()) {

				case 0: // inches/cm
					factor = 2.54;
					unit1="inches";
					unit2="cm";
					break;
				case 1: // Degrees/Radians
					factor = 0.0174;
					unit1="degree";
					unit2="radian";
					break;
				case 2: // Acres/Hectares
					factor = 0.4047;
					unit1="Acres";
					unit2="Hectares";
					break;
				case 3: // Miles/Kilometres
					factor = 1.609;
					unit1="Miles";
					unit2="Kilometres";
					break;
				case 4: // Yards/Metres
					factor = 0.9144;
					unit1="Yards";
					unit2="Metres";
					break;
				case 5: // Celsius/Fahrenheit
					factor = 1.8;
					offset = 32;
					unit1="Celsius";
					unit2="Fahrenheit";
					break;
				case 6: //Pounds/Kilogram
					factor=0.45;
					offset = 0;
					unit1="Pounds";
					unit2="kilogram";
					break;
				}

				result = factor * value + offset;
				count++;
				DecimalFormat df=new DecimalFormat("#.##");
				String temp=df.format(result);
				label.setText(temp);
				countlabel.setText("Number of conversions done= "+count);
				
				unitlabel1.setText(unit1);
				unitlabel2.setText(unit2);
				
				
			}
			
			}
			catch(NumberFormatException e)
			{
				String error;
				error="Please enter valid numbers only !!";
				JOptionPane.showMessageDialog(new JFrame(), error, "Error", JOptionPane.ERROR_MESSAGE);
			}
			
			
			
			
			if (textField.getText().isEmpty()==true)
			{
				String error;
				error="No Values Entered !!!";
				JOptionPane.showMessageDialog(new JFrame(), error, "Error", JOptionPane.ERROR_MESSAGE);
			}
			
		}

		
	}
	
	private class Convertclear implements ActionListener {
		
		public void actionPerformed(ActionEvent event) {
			textField.setText("");
			count=0;
			countlabel.setText("Number of conversions done= "+count);
			label.setText("");
			unitlabel1.setText("");
			unitlabel2.setText("");
			
		}
	}
	
}
