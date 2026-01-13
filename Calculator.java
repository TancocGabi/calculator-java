package problema3;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Arrays;

import javax.swing.*;
import javax.swing.border.LineBorder;

public class Calculator {
	//dimensiunile ferestrei
	int boardWidth = 360;
	int boardHeight = 540;
	
	//definim niste culori 
	
	Color customLightGray = new Color(212,212,210);
	Color customDarkGray = new Color(80,80,80);
	Color customBlack = new Color(28,28,28);
	Color customOrange = new Color(255,149,0);
	
	JLabel displayLabel = new JLabel(); //ecranul
	JPanel displayPanel = new JPanel(); //panou
	JPanel buttonsPanel = new JPanel();

	//A+B,A-B,A/B,A*B
	String A="0";
	String operator=null;
	String B=null;

	String[] buttonValues = {
	        "AC", "+/-", "%", "÷", 
	        "7", "8", "9", "×", 
	        "4", "5", "6", "-",
	        "1", "2", "3", "+",
	        "0", ".", "√", "="
	    };
	String[] rightSymbols = {"÷", "×", "-", "+", "="};
    String[] topSymbols = {"AC", "+/-", "%"};


	
	JFrame frame = new JFrame("Calculator");
	
	Calculator(){
		//frame.setVisible(true); //ca sa vedem fereastra
		frame.setSize(boardWidth,boardHeight);
		frame.setLocationRelativeTo(null); //centreaza fereastra
		frame.setResizable(false); //comanda este pusa ca utilizatorul sa nu poata modifica dimensiunea 
		//ferestrei cand este deschisa tragand de marginea acestuia
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //cand da pe close programul se va termina
		frame.setLayout(new BorderLayout()); //pentru a plasa componentele N/S/E/V
		
		displayLabel.setBackground(customBlack);
		displayLabel.setForeground(Color.white); //culoarea textului
		displayLabel.setFont(new Font("Arial", Font.PLAIN, 80)); //seteaza fontul textului din ecranul pc-ului la Arial, normal, marimea 80 px
		displayLabel.setHorizontalAlignment(JLabel.RIGHT); //afisam textul in partea dreapta 
		displayLabel.setText("0"); //un text default
		displayLabel.setOpaque(true); 
		
		displayPanel.setLayout(new BorderLayout());
		displayPanel.add(displayLabel); // punem text labelul in int panel1
		frame.add(displayPanel, BorderLayout.NORTH); //adaugam panel1 in fereastra noastra 
		
		buttonsPanel.setLayout(new GridLayout(5,4));
		buttonsPanel.setBackground(customBlack);
		frame.add(buttonsPanel);
		
		for(int i = 0; i < buttonValues.length; i++) {
			JButton button = new JButton();
			String buttonValue = buttonValues[i];
			button.setFont(new Font("Arial", Font.PLAIN, 30));
			button.setText(buttonValue);
			button.setFocusable(false);
			button.setBorder(new LineBorder(customBlack)); // seteaza culoarea marginilor butoanelor ca fiind neagra
			if(Arrays.asList(topSymbols).contains(buttonValue)) {
				button.setBackground(customLightGray);
				button.setForeground(customBlack);
			}
			else if(Arrays.asList(rightSymbols).contains(buttonValue)) {
				button.setBackground(customOrange);
				button.setForeground(Color.white);
			}
			else {
				button.setBackground(customDarkGray);
				button.setForeground(Color.white);
			}
			
			buttonsPanel.add(button);
			
			button.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) { //action e va fii un click al mouse-ului
					JButton button = (JButton) e.getSource(); //butonul care va fii apasat, evenimentul este un click, sursa click ului este jbutton
					String buttonValue = button.getText(); // luam simbolul ca sa vedem dupa ce buton a fost apasat
					if(Arrays.asList(rightSymbols).contains(buttonValue)) {
						if(buttonValue.equals("=")) {
							if(A != null) {
								B = displayLabel.getText();
								double numA = Double.parseDouble(A);
								double numB = Double.parseDouble(B);
								
								if(operator.equals("÷") && numB == 0) {
									JOptionPane.showMessageDialog(frame,"Nu poti imparti la zero!","Eroare!!",JOptionPane.ERROR_MESSAGE);
									clearAll();
									displayLabel.setText("0");
									return;
								}
								
								if(operator.equals("+")) {
									displayLabel.setText(removeZeroDecimal(numA + numB));
								}
								else if(operator.equals("-")) {
									displayLabel.setText(removeZeroDecimal(numA - numB));
								}
								else if(operator.equals("×")) {
									displayLabel.setText(removeZeroDecimal(numA * numB));
								}
								else if(operator.equals("÷")) {
									displayLabel.setText(removeZeroDecimal(numA / numB));
								}
								clearAll();
							}
						}
						else if("+-×÷".contains(buttonValue)) {
							if(operator == null) { //daca nu verific cu null utilizatorul poate apasa de 2 ori pe operator
								A = displayLabel.getText();
								displayLabel.setText("0");
								B = "0";	
							}
							operator = buttonValue;
						}
					}
					else if(Arrays.asList(topSymbols).contains(buttonValue)) {
						if(buttonValue.equals("AC")) {
							clearAll();
							displayLabel.setText("0");
						}
						else if(buttonValue.equals("+/-")) {
							double numDisplay = Double.parseDouble(displayLabel.getText());
							numDisplay *= -1;
							displayLabel.setText(removeZeroDecimal(numDisplay));
						}
						else if(buttonValue.equals("%")) {
							double numDisplay = Double.parseDouble(displayLabel.getText());
							numDisplay /= 100;
							displayLabel.setText(removeZeroDecimal(numDisplay));
						}
						
						
						
					}
					else {
						//ciferele
						if (buttonValue.equals("√")) {
							double numDisplay = Double.parseDouble(displayLabel.getText());
							
							if(numDisplay < 0) {
								//tratam cazul de radical din nr negativ
								JOptionPane.showMessageDialog(frame,"Nu poti calcula radical dintr-un numar negativ!","Eroare!",JOptionPane.ERROR_MESSAGE);
								return;
							}
							
							double result = Math.sqrt(numDisplay);
							displayLabel.setText(removeZeroDecimal(result));
						
							A=displayLabel.getText();
							operator = null;
							B = null;
						}
						else if(buttonValue.equals(".")) {
							if(!displayLabel.getText().contains(buttonValue)) { //daca nu are .
								displayLabel.setText(displayLabel.getText() + buttonValue);
							}
							
						}
						else if("0123456789".contains(buttonValue)){
							if(displayLabel.getText().equals("0")) {
								displayLabel.setText(buttonValue);
							}
							else {
								displayLabel.setText(displayLabel.getText() + buttonValue);
							}
							
						}
					}
					
				}
			});
			frame.setVisible(true); //ca sa vedem fereastra
		}
	}
	void clearAll() {
		A = "0";
		operator = null;
		B = null;
	}
	
	String removeZeroDecimal(double numDisplay) {
		if(numDisplay % 1 == 0 ) {
			return Integer.toString((int) numDisplay);
		}
		
		return Double.toString(numDisplay);
		
	}
}




