package com.haxorz.lab7;

import java.util.ArrayList;

public class AddCmd extends DirectoryCmd {
    public ArrayList<Employee> employeesToAdd;

    public AddCmd(ArrayList<Employee> fromJson) {
        super(DirectoryCmdType.Add);
        employeesToAdd = fromJson;
    }
}
