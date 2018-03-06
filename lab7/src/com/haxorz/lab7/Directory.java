package com.haxorz.lab7;

import java.util.HashSet;
import java.util.Set;

public abstract class Directory {

	//set because there cannot be identical people
	//and even if there were, itd be rather useless
	private Set<Employee> _dir = new HashSet<>();

	public boolean add(Employee e){
		return _dir.add(e);
	}
	public boolean print(){
		if (_dir.isEmpty()) System.out.println("<empty directory>");
		for(Employee e : _dir){
			System.out.println(e.toString());
		}
		return true;
	}
	public void clear(){
		_dir.clear();
	}
}
