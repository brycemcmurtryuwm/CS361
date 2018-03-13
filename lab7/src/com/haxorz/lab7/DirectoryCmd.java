package com.haxorz.lab7;

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

    public static DirectoryCmd ParseFromString(String line) {
        String[] tmpArr = line.trim().split("\\s+");

        if(tmpArr.length == 0)
            return null;

        switch (tmpArr[0].toLowerCase()){
            case "clr":
                return new DirectoryCmd(DirectoryCmdType.Clear);
            case "add":
                return new DirectoryCmd(DirectoryCmdType.Add);
            case "end":
                return new DirectoryCmd(DirectoryCmdType.End);
            case "print":
                return new DirectoryCmd(DirectoryCmdType.Print);
            default:
                //firstName lastName DEPT Phone

                if(tmpArr.length != 4)
                    return null;

                String firstName, lastName, dept, phone;

                firstName = tmpArr[0];
                lastName = tmpArr[1];
                dept = tmpArr[2];
                phone = tmpArr[3];

                return new DirectoryCmd(new Employee(firstName, lastName, dept, phone, title, gender));
        }

    }
}

