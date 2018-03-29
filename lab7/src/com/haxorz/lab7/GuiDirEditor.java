package com.haxorz.lab7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiDirEditor extends JFrame{

	private JTextField givenNameField;
	private JTextField surnameField;
	private JTextField deptField;
	private JTextField phoneField;
	private JComboBox<Title> titleList;
	private ButtonGroup genderButtons;

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

		givenNameField = new JTextField();
		surnameField = new JTextField();
		deptField = new JTextField();
		phoneField = new JTextField();


		//the radioButtons for gender
		JPanel gendersPanel = new JPanel(new FlowLayout());

		JRadioButton maleButton = new JRadioButton("Male");
		maleButton.setActionCommand("Male");
		maleButton.setSelected(true);
		JRadioButton femaleButton = new JRadioButton("Female");
		femaleButton.setActionCommand("Female");
		JRadioButton otherButton = new JRadioButton("Other");
		otherButton.setActionCommand("Other");

		genderButtons = new ButtonGroup();
		genderButtons.add(maleButton);
		genderButtons.add(femaleButton);
		genderButtons.add(otherButton);

		gendersPanel.add(maleButton);
		gendersPanel.add(femaleButton);
		gendersPanel.add(otherButton);

		//drop down list for titles
		titleList = new JComboBox<>(Title.values());

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
		JButton print = new JButton("Print");
		JButton clear = new JButton("Clear");
		JButton submit = new JButton("Submit");
		JButton exit = new JButton("Exit");

		submit.addActionListener(new submitListener());
		exit.addActionListener(e -> CloseWindow());
		print.addActionListener(e -> Client.sendCmd(DirectoryCmdType.Print));
		clear.addActionListener(e -> Client.sendCmd(DirectoryCmdType.Clear));

		buttons.add(print);
		buttons.add(clear);
		buttons.add(submit);
		buttons.add(exit);

		this.add(buttons, BorderLayout.PAGE_END);
	}

	private void CloseWindow() {
		super.dispose();
	}

	public class submitListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			Employee employee = new Employee(givenNameField.getText(), surnameField.getText(), deptField.getText(), phoneField.getText(),
					titleList.getItemAt(titleList.getSelectedIndex()), Gender.valueOf(genderButtons.getSelection().getActionCommand()));

			Client.sendEmployeesToDir(employee);
		}
	}

	public static void main(String[] args){
		new GuiDirEditor();
	}

}

