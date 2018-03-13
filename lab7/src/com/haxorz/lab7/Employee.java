package com.haxorz.lab7;

public class Employee implements Comparable<Employee>{
	private String _firstName;
	private String _lastName;
	private String _department;
	private String _phoneNumber;

	private Title _title;
	private Gender _gender;

	public Employee(String firstName, String lastName, String department, String phoneNumber, Title title, Gender gender) {
		_firstName = firstName;
		_lastName = lastName;
		_department = department;
		_phoneNumber = phoneNumber;
		_title = title;
		_gender = gender;
	}

	public String getFirstName() {
		return _firstName;
	}

	public void setFirstName(String firstName) {
		_firstName = firstName;
	}

	public String getLastName() {
		return _lastName;
	}

	public void setLastName(String lastName) {
		_lastName = lastName;
	}

	public String getDepartment() {
		return _department;
	}

	public void setDepartment(String department) {
		_department = department;
	}

	public String getPhoneNumber() {
		return _phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		_phoneNumber = phoneNumber;
	}

	@Override
	public String toString() {
		return _lastName + ", " + _firstName + " " + _phoneNumber + " " + _department;
	}

	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(o == null || getClass() != o.getClass()) return false;

		Employee employee = (Employee) o;

		if(_firstName != null ? !_firstName.equals(employee._firstName) : employee._firstName != null) return false;
		if(_lastName != null ? !_lastName.equals(employee._lastName) : employee._lastName != null) return false;
		if(_department != null ? !_department.equals(employee._department) : employee._department != null) return false;
		return _phoneNumber != null ? _phoneNumber.equals(employee._phoneNumber) : employee._phoneNumber == null;
	}

	@Override
	public int hashCode() {
		int result = _firstName != null ? _firstName.hashCode() : 0;
		result = 31 * result + (_lastName != null ? _lastName.hashCode() : 0);
		result = 31 * result + (_department != null ? _department.hashCode() : 0);
		result = 31 * result + (_phoneNumber != null ? _phoneNumber.hashCode() : 0);
		return result;
	}

	@Override
	public int compareTo(Employee o) {
		return this._lastName.compareToIgnoreCase(o._lastName);
	}

	public Title getTitle() {
		return _title;
	}

	public Gender getGender() {
		return _gender;
	}
}
