package com.haxorz.lab7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiDirEditor extends JFrame{

	public GuiDirEditor(){
		this.setSize(500,500);
		createComponents();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void createComponents(){
		JPanel datafields = new JPanel(new GridLayout(6,2));
		JLabel givenNameLabel = new JLabel("Given Name");
		JLabel surnameLabel = new JLabel("Surname");
		JLabel deptLabel = new JLabel("Department");
		JLabel phoneLabel = new JLabel("Phone");
		JLabel genderLabel = new JLabel("Gender");
		JLabel titleLabel = new JLabel("Title");

		JTextField givenNameField = new JTextField();
		JTextField surnameField = new JTextField();
		JTextField deptField = new JTextField();
		JTextField phoneField = new JTextField();


		//the radioButtons for gender
		JPanel gendersPanel = new JPanel(new FlowLayout());

		JRadioButton maleButton = new JRadioButton("Male");
		JRadioButton femaleButton = new JRadioButton("Female");
		JRadioButton otherButton = new JRadioButton("Other");

		ButtonGroup genderButtons = new ButtonGroup();
		genderButtons.add(maleButton);
		genderButtons.add(femaleButton);
		genderButtons.add(otherButton);

		gendersPanel.add(maleButton);
		gendersPanel.add(femaleButton);
		gendersPanel.add(otherButton);

		//drop down list for titles
		String[] titleStrings = { "Mr", "Ms", "Mrs", "Dr", "Col", "Prof" };
		JComboBox titleList = new JComboBox(titleStrings);

		datafields.add(givenNameLabel);
		datafields.add(givenNameField);
		datafields.add(surnameLabel);
		datafields.add(surnameField);
		datafields.add(deptLabel);
		datafields.add(deptField);
		datafields.add(phoneLabel);
		datafields.add(phoneField);
		datafields.add(genderLabel);
		datafields.add(gendersPanel);
		datafields.add(titleLabel);
		datafields.add(titleList);

		this.add(datafields, BorderLayout.CENTER);

		JPanel buttons = new JPanel(new FlowLayout());
		JButton submit = new JButton("Submit");
		JButton exit = new JButton("Exit");

		buttons.add(submit);
		buttons.add(exit);

		this.add(buttons, BorderLayout.PAGE_END);



	}

	public class submitListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {




			return;
		}
	}

	public static void main(String[] args){
		new GuiDirEditor();
	}

}

