package com.haxorz.lab7;

import java.util.List;

public class DirectoryProxy implements Directory {

    private MainDirectory _directory = new MainDirectory();


    @Override
    public boolean add(String e) {
        return _directory.add(e);
    }

    @Override
    public boolean print() {
        return _directory.print();
    }

    @Override
    public void clear() {
        _directory.clear();
    }

    @Override
    public List<Employee> getAllEmployees() {
        return _directory.getAllEmployees();
    }
}
