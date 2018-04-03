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
		this.setSize(600,500);
		this.setTitle("Haxors Directory Editor");
		createComponents();
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void createComponents(){
		JPanel datafields = new JPanel(new GridLayout(6,2));

		Font font = new Font("SansSerif", Font.BOLD, 20);

		JLabel givenNameLabel = new JLabel("Given Name");
		givenNameLabel.setFont(font);
		JLabel surnameLabel = new JLabel("Surname");
		surnameLabel.setFont(font);
		JLabel deptLabel = new JLabel("Department");
		deptLabel.setFont(font);
		JLabel phoneLabel = new JLabel("Phone");
		phoneLabel.setFont(font);
		JLabel genderLabel = new JLabel("Gender");
		genderLabel.setFont(font);
		JLabel titleLabel = new JLabel("Title");
		titleLabel.setFont(font);

		givenNameField = new JTextField();
		surnameField = new JTextField();
		deptField = new JTextField();
		phoneField = new JTextField();

		givenNameField.setFont(font);
		surnameField.setFont(font);
		deptField.setFont(font);
		phoneField.setFont(font);


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

		maleButton.setFont(font);
		femaleButton.setFont(font);
		otherButton.setFont(font);

		gendersPanel.add(maleButton);
		gendersPanel.add(femaleButton);
		gendersPanel.add(otherButton);

		//drop down list for titles

		titleList = new JComboBox<>(Title.values());
		titleList.setFont(font);

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
		submit.setFont(font);
		print.setFont(font);
		clear.setFont(font);
		JButton exit = new JButton("Exit");
		exit.setFont(font);

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

		System.exit(0);
	}

	public class submitListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			String givenName = givenNameField.getText();
			String surname = surnameField.getText();
			String department = deptField.getText();
			String phone = phoneField.getText();

			if(givenName.equals("")){
				JOptionPane.showMessageDialog(new Frame(), "Given Name field is empty");
				return;
			}
			if(surname.equals("")){
				JOptionPane.showMessageDialog(new Frame(), "Surname field is empty");
				return;
			}
			if(department.equals("")){
				JOptionPane.showMessageDialog(new Frame(), "Department field is empty");
				return;
			}
			if(phone.equals("")){
				JOptionPane.showMessageDialog(new Frame(), "Phone field is empty");
				return;
			}

			Employee employee = new Employee(givenName, surname, department, phone,
					titleList.getItemAt(titleList.getSelectedIndex()), Gender.valueOf(genderButtons.getSelection().getActionCommand()));

			Client.sendEmployeesToDir(employee);
		}
	}

	public static void main(String[] args){
		new GuiDirEditor();
	}

}

