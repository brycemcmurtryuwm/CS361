package com.haxorz.lab7;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GuiDirEditor extends JFrame{

	private DirectoryEditor _directoryEditor;

	private JTextField _givenNameField;
	private JTextField _surnameField;
	private JTextField _deptField;
	private JTextField _phoneField;
	private JRadioButton _maleButton;
	private JRadioButton _femaleButton;
	private JRadioButton _otherButton;
	private JComboBox _titleList;

	public GuiDirEditor(){
		_directoryEditor = new DirectoryEditor(System.out);
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

		_givenNameField = new JTextField();
		_givenNameField.setFont(font);
		_surnameField = new JTextField();
		_surnameField.setFont(font);
		_deptField = new JTextField();
		_deptField.setFont(font);
		_phoneField = new JTextField();
		_phoneField.setFont(font);


		//the radioButtons for gender
		JPanel gendersPanel = new JPanel(new FlowLayout());

		_maleButton = new JRadioButton("Male");
		_maleButton.setFont(font);
		_femaleButton = new JRadioButton("Female");
		_femaleButton.setFont(font);
		_otherButton = new JRadioButton("Other");
		_otherButton.setFont(font);

		ButtonGroup genderButtons = new ButtonGroup();
		genderButtons.add(_maleButton);
		genderButtons.add(_femaleButton);
		genderButtons.add(_otherButton);

		gendersPanel.add(_maleButton);
		gendersPanel.add(_femaleButton);
		gendersPanel.add(_otherButton);

		//drop down list for titles
		String[] titleStrings = { "Mr", "Ms", "Mrs", "Dr", "Col", "Prof" };
		_titleList = new JComboBox(titleStrings);
		_titleList.setFont(font);

		datafields.add(givenNameLabel);
		datafields.add(_givenNameField);
		datafields.add(surnameLabel);
		datafields.add(_surnameField);
		datafields.add(deptLabel);
		datafields.add(_deptField);
		datafields.add(phoneLabel);
		datafields.add(_phoneField);
		datafields.add(genderLabel);
		datafields.add(gendersPanel);
		datafields.add(titleLabel);
		datafields.add(_titleList);

		this.add(datafields, BorderLayout.CENTER);

		JPanel buttons = new JPanel(new FlowLayout());
		JButton submit = new JButton("Submit");
		submit.setFont(font);
		JButton exit = new JButton("Exit");
		exit.setFont(font);

		buttons.add(submit);
		buttons.add(exit);

		this.add(buttons, BorderLayout.PAGE_END);

		submit.addActionListener(new submitListener());
		exit.addActionListener(new exitListener());



	}

	public class submitListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {

			String givenName = _givenNameField.getText();
			String surname = _surnameField.getText();
			String department = _deptField.getText();
			String phone = _phoneField.getText();

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
			if(!(_maleButton.isSelected() || _femaleButton.isSelected() || _otherButton.isSelected())){
				JOptionPane.showMessageDialog(new Frame(), "No gender selected");
				return;
			}

			//determine gender
			Gender gender;
			if(_maleButton.isSelected()) gender = Gender.Male;
			else if(_femaleButton.isSelected()) gender = Gender.Female;
			else if(_otherButton.isSelected()) gender = Gender.Other;
			else return;

			Title title;
			switch((String)_titleList.getSelectedItem()){
				case("Mr"):{
					title = Title.Mr;
					break;
				}
				case("Ms"):{
					title = Title.Ms;
					break;
				}
				case("Mrs"):{
					title = Title.Mrs;
					break;
				}
				case("Dr"):{
					title = Title.Dr;
					break;
				}
				case("Col"):{
					title = Title.Col;
					break;
				}
				case("Prof"):{
					title = Title.Prof;
					break;
				}
				default: return;
			}


			Employee entered = new Employee(givenName, surname, department, phone, title, gender);

			_directoryEditor.executeCmd(new DirectoryCmd(entered));

			return;
		}
	}
	public class exitListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			System.exit(0);
		}
	}

	public static void main(String[] args){
		new GuiDirEditor();
	}

}

