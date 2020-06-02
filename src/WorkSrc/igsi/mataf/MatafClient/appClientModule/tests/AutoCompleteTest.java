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
	private Object[] comboList1 = {"1 - ����",
								  "2 - �����",
								  "3 - �����",
								  "12 - ������ �",
								  "123 -  ������ � ����"								  
								  };
								  
	private Object[] comboList2 = {"1 - ���� �",
								  "2 - ���� �",
								  "3 - ���� �",
								  "12 - ���� �",
								  "123 -  ���� � ����"								  
								  };
								  
	private Object[] comboList3 = {"1 - ����� �",
								  "2 - ����� �",
								  "3 - ����� �",
								  "12 - ����� �",
								  "123 -  ����� � ����"								  
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
		
		comboLabel = new JLabel("��� ��� ����");
		comboLabel.setComponentOrientation(java.awt.ComponentOrientation.RIGHT_TO_LEFT);
		
		changeableComboLabel = new JLabel("��� �� ���");
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

