package tests;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class AutoCompleteTest {
	
	private JComboBoxExt comboBox;
	private JComboBoxExt changeableComboBox;
	private JLabel comboLabel;
	private JLabel changeableComboLabel;
	private JPanel mainPanel;
	private JFrame frame;
	private Object[] comboList1 = {"1 - אישי",
								  "2 - עיסקי",
								  "3 - בנקאי",
								  "12 - ניסיון א",
								  "123 -  ניסיון ב ארוך"								  
								  };
								  
	private Object[] comboList2 = {"1 - אישי א",
								  "2 - אישי ב",
								  "3 - אישי ג",
								  "12 - אישי ד",
								  "123 -  אישי ב ארוך"								  
								  };
								  
	private Object[] comboList3 = {"1 - עיסקי א",
								  "2 - עיסקי ב",
								  "3 - עיסקי ג",
								  "12 - עיסקי ד",
								  "123 -  עיסקי ב ארוך"								  
								  };
	
	public AutoCompleteTest() {
		comboBox = new JComboBoxExt(new DefaultComboBoxModel(comboList1));
		comboBox.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
		comboBox.addActionListener(new ActionListener() {
		    public void actionPerformed(ActionEvent e) {
		        JComboBox cb = (JComboBox)e.getSource();
		        String modelName = (String)cb.getSelectedItem();
		        if (modelName.startsWith("1")) {
		        	changeableComboBox.setModel(new DefaultComboBoxModel(comboList2));
		        	changeableComboBox.repaint();
		        } else {
		        	changeableComboBox.setModel(new DefaultComboBoxModel(comboList3));
		        	changeableComboBox.repaint();
		        }
		    }
		});


		changeableComboBox = new JComboBoxExt(new DefaultComboBoxModel(comboList2));
		changeableComboBox.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
		
		comboLabel = new JLabel("בחר סוג פנקס");
		comboLabel.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
		
		changeableComboLabel = new JLabel("בחר תת סוג");
		changeableComboLabel.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
		
		mainPanel = new JPanel(new GridLayout(0,2));
		mainPanel.add(comboBox);
		mainPanel.add(comboLabel);
		mainPanel.add(changeableComboBox);
		mainPanel.add(changeableComboLabel);
		
		frame = new JFrame("AutoCompletion Tester");
		frame.getContentPane().add(mainPanel);
	}

	public void run() {
		frame.pack();
		frame.setVisible(true);
	}
	

	public static void main(String[] args) {
		AutoCompleteTest tester = new AutoCompleteTest();
		tester.run();		
	}

}

