package com.haxorz.lab7;


import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Reads in commands from either a file or user input
 * and edits a directory file.
 */
public class DirectoryEditor {

    private PrintStream out;
    private DirectoryCmdType _mode = DirectoryCmdType.End;

    private List<Employee> employeesToAdd = new ArrayList<>();
    private DirectoryProxy _directory = new DirectoryProxy();

    public DirectoryEditor(PrintStream out) {
        this.out = out;
    }


    public void executeCmd(DirectoryCmd cmd) {
        switch (cmd.Type){
            case Add:
                _mode = DirectoryCmdType.Add;
                break;
            case Clear:
                _directory.clear();
                break;
            case End:
                _directory.add(Transport.EmployeesAsJson(employeesToAdd));
                employeesToAdd.clear();
                break;
            case Print:
                _directory.print();
                break;
            case User:
                if(_mode == DirectoryCmdType.Add){
                    employeesToAdd.add(cmd.Employee);
                }
                break;
        }
    }
}

