package com.haxorz.lab7;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.istack.internal.NotNull;

import java.util.ArrayList;
import java.util.Collection;

public class DirectoryCmd {

    public DirectoryCmdType Type;
    public com.haxorz.lab7.Employee Employee = null;

    DirectoryCmd(DirectoryCmdType type){

        Type = type;
    }

    DirectoryCmd(Employee e){

        Employee = e;
        Type = DirectoryCmdType.User;
    }

    public static DirectoryCmd ParseFromString(@NotNull String line) {
        String[] tmpArr = line.trim().split("\\s+");

        if(tmpArr.length == 0)
            return null;

        switch (tmpArr[0].toLowerCase()){
            case "clr":
            case "clear":
                return new DirectoryCmd(DirectoryCmdType.Clear);
            case "add":
                if(tmpArr.length>1)
                {
                    //Add Json and convert
                    String json = tmpArr[1];


                    Gson g = new Gson();
                    ArrayList<Employee> fromJson = g.fromJson(json,
                            new TypeToken<Collection<Employee>>() {
                            }.getType());
                    return new AddCmd(fromJson);
                }

                return new DirectoryCmd(DirectoryCmdType.Add);
            case "end":
                return new DirectoryCmd(DirectoryCmdType.End);
            case "print":
                return new DirectoryCmd(DirectoryCmdType.Print);
            default:
                //firstName lastName DEPT Phone

                if(tmpArr.length != 6)
                    return null;

                String firstName, lastName, dept, phone;

                firstName = tmpArr[0];
                lastName = tmpArr[1];
                dept = tmpArr[2];
                phone = tmpArr[3];

                Title title = Title.valueOf(tmpArr[4]);
                Gender gender = Gender.valueOf(tmpArr[5]);

                return new DirectoryCmd(new Employee(firstName, lastName, dept, phone, title, gender));
        }

    }
}

