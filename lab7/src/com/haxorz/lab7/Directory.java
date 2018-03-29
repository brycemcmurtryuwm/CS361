package com.haxorz.lab7;

import java.util.Collection;
import java.util.List;

public interface Directory {

	boolean add(String employees);

	boolean print();

	void clear();

    List<Employee> getAllEmployees();
}
